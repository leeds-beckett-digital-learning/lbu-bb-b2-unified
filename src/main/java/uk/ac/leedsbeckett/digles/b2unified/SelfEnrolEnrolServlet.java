/*
 * Copyright 2022 Leeds Beckett University.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.leedsbeckett.digles.b2unified;

import blackboard.data.ValidationException;
import blackboard.data.course.Course;
import blackboard.data.course.CourseMembership;
import blackboard.persist.PersistenceException;
import blackboard.persist.course.CourseDbLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jon
 */
public class SelfEnrolEnrolServlet extends HttpServlet
{

  @Override
  protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
  {
    resp.setContentType( "text/plain" );
    ServletOutputStream out = resp.getOutputStream();
    out.print( "This servlet does not work with the GET method. It responds to a POST method request." );
  }

  @Override
  protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
  {
    WebAppCore webappcore = WebAppCore.getInstance( req.getServletContext() );

    ObjectMapper mapper = new ObjectMapper();
    mapper.enable( SerializationFeature.INDENT_OUTPUT );
    ResponsePOJO r = new ResponsePOJO();

    
    r.error = false;
    r.courserequest       = "/course/".equals( req.getPathInfo() );
    r.organizationrequest = "/organization/".equals( req.getPathInfo() );
    r.courseid = req.getParameter( "courseid" );
    r.reason   = req.getParameter( "reason" );
    if ( r.courserequest )
      r.emailtemplate = webappcore.configproperties.getCourseSelfEnrolReceiptEmail();
    else
      r.emailtemplate = webappcore.configproperties.getOrganizationSelfEnrolReceiptEmail();
      
    try
    {
      if ( r.courserequest || r.organizationrequest )
        process( r );
      else
      {
        r.error = true;
        r.errormessage = "A configuration error prevented this from working." + req.getPathInfo();          
      }
    }
    catch ( Exception e )
    {
      webappcore.logger.error( "Exception while trying to enrol user", e );
      r.error = true;
      r.errormessage = "Technical fault attempting to process request.";
    }
    
    resp.setContentType( "application/json" );
    ServletOutputStream out = resp.getOutputStream();
    mapper.writeValue( out, r );
  }

  public void process( ResponsePOJO r ) throws PersistenceException, ValidationException, MessagingException
  {
    BaseOutcomes outcomes = new BaseOutcomes();
    if ( !outcomes.staff )
    {
      r.error = true;
      r.errormessage = "Self enrollment only available to users whose primary role is staff.";
      return;
    }
    Course course = CourseDbLoader.Default.getInstance().loadByCourseId( r.courseid );
    if ( Utils.isMember( course, outcomes.user ) )
    {
      r.error = true;
      r.errormessage = "You are already enrolled on " + course.getTitle() + ".";
      return;
    }
    r.successmessage = "Enrolment on " + course.getTitle() + " was completed and \n" +
            "you will find it on the list if you refresh the page. \n" +
            "Your enrolment has been logged for the purposes of auditing.\n";
    Utils.enrollUser( course, outcomes.user, CourseMembership.Role.INSTRUCTOR );
    String emailcontent = getEmailContent( outcomes, r, course );
    sendAcknowledgmentToDigles( outcomes, r, emailcontent );
  }

  final static String EMAILTEMPLATE = 
          "Hello, \n" +
          "${name} (${username}) has been added as an instructor to the MyBeckett ${type} ${coursename}.\n" +
          "Reason code for access: ${reason}.\n" +
          "If you have enrolled yourself on this ${type} in error and would like to be removed from the ${type} please contact the Digital Learning Service for further help.\n" +
          "Our email: digitallearning@leedsbeckett.ac.uk\n" +
          "Phone: 0113 812 5410\n" +
          "If you want to add colleagues to this ${type}, please follow the Adding Users Guide available at:\n" +
          " https://teachlearn.leedsbeckett.ac.uk/addusers/\n" +
          "Regards,\nDigital Learning Service";
  
  public String getEmailContent( BaseOutcomes outcomes, SelfEnrolEnrolServlet.ResponsePOJO r, Course course )
  {
    String content = r.emailtemplate;
    content = content.replaceAll( "\r", "" );
    content = content.replaceAll( "</p>", "\n" );  // End of para replaced with new line
    content = content.replaceAll( "<[^>]*>", "" ); // All other tags stripped out
    content = content.replaceAll( "&nbsp;", " " ); // nbsp Entity replaced with space.
    content = content.replaceAll( "\\$\\{name}", outcomes.getName() );
    content        = content.replaceAll( "\\$\\{username}", outcomes.getUserName() );
    content        = content.replaceAll( "\\$\\{coursename}", course.getTitle() );    
    content        = content.replaceAll( "\\$\\{reason}", r.reason );    
    content        = content.replaceAll( "\\$\\{type}", r.courserequest?"module":"course group" ); 
    return content;
  }
  
  public void sendAcknowledgmentToDigles( BaseOutcomes outcomes, SelfEnrolEnrolServlet.ResponsePOJO r, String content ) throws PersistenceException, ValidationException, AddressException, MessagingException
  {
    String subject = r.courserequest?
            "*** Module Self-Enrol ***":
            "*** Group Self-Enrol ***";
    String address = outcomes.getUserEmail();
    String us    = outcomes.webappcore.configproperties.getEmail();
    
    outcomes.webappcore.logger.debug( "Send email with subject " + subject );
    outcomes.webappcore.logger.debug( "To " + us + "," + address );
    outcomes.webappcore.logger.debug( content );
    
    // Temporarily bodge email addresses for development
    // address = "j.r.maber@leedsbeckett.ac.uk";
    
    InternetAddress[] recipients = { new InternetAddress( address ) };
    InternetAddress[] cclist     = { new InternetAddress( us )  };
    InternetAddress fromia       = new InternetAddress( address );
    
    outcomes.webappcore.sendPlainEmail( subject, fromia, null, recipients, cclist, content );
  } 
  
  
  public class ResponsePOJO
  {
    boolean  error;
    String   errormessage;
    boolean  courserequest;
    boolean  organizationrequest;
    String   courseid;
    String   reason;
    String   successmessage;
    String   emailtemplate;

    public boolean isError()
    {
      return error;
    }

    public String getErrormessage()
    {
      return errormessage;
    }

    public String getSuccessmessage()
    {
      return successmessage;
    }

    public String getEmailtemplate()
    {
      return emailtemplate;
    }
    
    
  }

}
