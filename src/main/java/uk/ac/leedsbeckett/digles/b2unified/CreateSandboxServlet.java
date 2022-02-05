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

import blackboard.base.InitializationException;
import blackboard.data.ValidationException;
import blackboard.data.course.Course;
import blackboard.data.course.CourseMembership;
import blackboard.data.course.CourseMembership.Role;
import blackboard.data.user.User;
import blackboard.db.ConstraintViolationException;
import blackboard.persist.PersistenceException;
import blackboard.persist.course.CourseDbPersister;
import blackboard.persist.course.CourseMembershipDbPersister;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static uk.ac.leedsbeckett.digles.b2unified.SandboxOutcomes.buildSandboxCourseID;
/**
 *
 * @author jon
 */
public class CreateSandboxServlet extends HttpServlet
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

  public void process( ResponsePOJO r ) throws PersistenceException, ValidationException, ConstraintViolationException, InitializationException
  {
    BaseOutcomes outcomes = new BaseOutcomes();
    if ( !outcomes.staff )
    {
      r.error = true;
      r.errormessage = "Sandbox creation only available to users whose primary role is staff.";
      return;
    }
    
    CourseDbPersister coursepersister = CourseDbPersister.Default.getInstance();

    r.courseid = buildSandboxCourseID( outcomes.webappcore.configproperties.getTrainingCoursePrefix(), outcomes.user.getUserName() );
    r.coursetitle = "Training Sandbox for " + outcomes.user.getUserName();
    
    Course course = new Course();    
    course.setCourseId( r.courseid );
    course.setBatchUid( r.courseid );
    course.setTitle( r.coursetitle );
    course.setInstitutionName( outcomes.settings.getInstitutionName() );
    course.setDescription( "For use in the module with training modules." );
    course.setIsAvailable( true );
    coursepersister.persist( course );

    if ( course.getId() != null )
      enrollUser( course, outcomes.user, Role.INSTRUCTOR );
    else
    {
      r.error = true;
      r.errormessage = "Unable to find course after creating it.";
    } 
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
    String coursetitle;
    boolean error;
    String errormessage;

    public String getCourseid()
    {
      return courseid;
    }

    public String getCoursetitle()
    {
      return coursetitle;
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
