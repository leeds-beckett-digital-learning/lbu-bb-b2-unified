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
import blackboard.data.course.CourseMembership.Role;
import blackboard.data.user.User;
import blackboard.persist.KeyNotFoundException;
import blackboard.persist.PersistenceException;
import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.course.CourseMembershipDbLoader;
import blackboard.persist.course.CourseMembershipDbPersister;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jon
 */
public class EnrolOnTrainingServlet extends HttpServlet
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

    r.courseid = req.getParameter( "courseid" );
    r.error = false;
    r.errormessage = "No error";

    try
    {
      process( r );
    }
    catch ( Exception e )
    {
      r.error = true;
      r.errormessage = "There was a technical problem attempting to enrol you.";
      webappcore.logger.error( "Attempting to enrol user.", e );
    }

    resp.setContentType( "application/json" );
    ServletOutputStream out = resp.getOutputStream();
    mapper.writeValue( out, r );
  }

  public void process( ResponsePOJO r ) throws PersistenceException, ValidationException
  {
    BaseOutcomes outcomes = new BaseOutcomes();
    if ( !outcomes.staff )
    {
      r.error = true;
      r.errormessage = "Self-enrol only available to users whose primary role is staff.";
      return;
    }    
    
    Course course = CourseDbLoader.Default.getInstance().loadByCourseId( r.courseid );
    
    if ( isMember( course, outcomes.user ) )
    {
      r.error = true;
      r.errormessage = "You are already enrolled on the course " + course.getTitle() + ".";
      return;
    }
      
    enrollUser( course, outcomes.user, Role.STUDENT );
  }

  public boolean isMember( Course course, User user )
          throws PersistenceException, ValidationException
  {
    CourseMembership membership;
    try
    {
      membership = CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId( course.getId(), user.getId() );
    }
    catch ( KeyNotFoundException ex)
    {
      return false;
    }
    return membership != null;
  }
  
  public void enrollUser( Course course, User user, Role role )
          throws PersistenceException, ValidationException
  {
    CourseMembershipDbPersister cmPersister = CourseMembershipDbPersister.Default.getInstance();
    CourseMembership membership = new CourseMembership();
    membership.setCourseId( course.getId() );
    membership.setUserId( user.getId() );
    membership.setRole( role );
    cmPersister.persist( membership );
  }

  public class ResponsePOJO
  {
    String courseid;
    boolean error;
    String errormessage;

    public String getCourseid()
    {
      return courseid;
    }

    public boolean isError()
    {
      return error;
    }

    public String getErrormessage()
    {
      return errormessage;
    }
  }

}
