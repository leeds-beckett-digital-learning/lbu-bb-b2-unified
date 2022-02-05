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

import blackboard.data.course.Course;
import blackboard.persist.Id;
import blackboard.persist.PersistenceException;
import blackboard.persist.SearchOperator;
import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.course.CourseSearch;
import blackboard.persist.course.CourseSearch.SearchKey;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jon
 */
public class SelfEnrolSearchServlet extends HttpServlet
{
  WebAppCore core;

  @Override
  public void init( ServletConfig config ) throws ServletException
  {
    super.init( config );
    this.core = WebAppCore.getInstance( config.getServletContext() );
  }
  
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
    ObjectMapper mapper = new ObjectMapper();
    mapper.enable( SerializationFeature.INDENT_OUTPUT );
    ResponsePOJO r = new ResponsePOJO();

    BaseOutcomes outcomes = new BaseOutcomes();

    core.logger.info(  "getCourseSelfEnrolValidationPattern() = " + outcomes.settings.getCourseSelfEnrolValidationPattern().pattern() );
    core.logger.info(  "getOrganizationSelfEnrolValidationPattern() = " + outcomes.settings.getOrganizationSelfEnrolValidationPattern().pattern() );
    
    r.error = false;
    r.courserequest       = "/course/".equals( req.getPathInfo() );
    r.organizationrequest = "/organization/".equals( req.getPathInfo() );
    r.searchcode = req.getParameter( "code" );
    if ( r.searchcode != null )
      r.searchcode = r.searchcode.trim();
    
    if ( !outcomes.staff )
    {
      r.error = true;
      r.errormessage = "Self enrollment only available to users whose primary role is staff.";
    }
    else if ( !r.courserequest && !r.organizationrequest )
    {
      r.error = true;
      r.errormessage = "A configuration error prevented this from working." + req.getPathInfo();
    }
    else if ( r.searchcode == null || r.searchcode.trim().length() == 0 )
    {
      r.error = true;
      r.errormessage  = "No code provided in box. ";
    }
    else if ( r.courserequest && 
              !outcomes.settings.getCourseSelfEnrolValidationPattern().matcher( r.searchcode ).matches() )
    {
      r.error = true;
      r.errormessage  = "Invalid specifier for search.";      
    }
    else if ( r.organizationrequest &&
              !outcomes.settings.getOrganizationSelfEnrolValidationPattern().matcher( r.searchcode ).matches() )
    {
      r.error = true;
      r.errormessage  = "Invalid specifier for search.";      
    }
    else
    {
      try
      {
        if ( r.courserequest )
          doSearch( outcomes.settings, r, Course.ServiceLevel.FULL );
        if ( r.organizationrequest )
          doSearch( outcomes.settings, r, Course.ServiceLevel.COMMUNITY );
      }
      catch ( Exception e )
      {
        core.logger.error( "Exception trying to search courses or organizations.", e );
        r.error = true;
        r.errormessage  = "A technical fault occured and the search was abandoned. ";
      }
    }
    
    resp.setContentType( "application/json" );
    ServletOutputStream out = resp.getOutputStream();
    mapper.writeValue( out, r );
  }

  void doSearch( UnifiedBuildingBlockProperties settings, ResponsePOJO r, Course.ServiceLevel servicelevel ) throws PersistenceException
  {
    ArrayList<Course> courselist;
    CourseSearch cs = CourseSearch.getViewCatalogSearch(
            SearchKey.CourseId, 
            SearchOperator.StartsWith, 
            r.searchcode + "-", 
            SearchOperator.LessThan, 
            (Date)null, 
            (Id)null, 
            servicelevel );
    courselist = (ArrayList<Course>)CourseDbLoader.Default.getInstance().loadByCourseSearch(cs);
    
    core.logger.debug( "Searching..." );
    ArrayList<Course> shortlist = new ArrayList<>();
    Pattern pattern = Course.ServiceLevel.COMMUNITY.equals( servicelevel ) ?
                      settings.getOrganizationSelfEnrolFilterPattern() :
                      settings.getCourseSelfEnrolFilterPattern() ;
    for ( Course c : courselist )
    {
      String cid = c.getCourseId();
      core.logger.debug( "   Checking " + cid + " against " + pattern.pattern() );
      if ( pattern.matcher( cid ).matches() )
        shortlist.add( c );
    }
    r.titles = new String[shortlist.size()];
    r.courseids = new String[shortlist.size()];
    for ( int i=0; i<shortlist.size(); i++ )
    {
      Course c = shortlist.get( i );
      r.courseids[i] = c.getCourseId();
      r.titles[i]    = c.getTitle();
    }
  }

  public class ResponsePOJO
  {
    boolean  error;
    String   errormessage;
    boolean  courserequest;
    boolean  organizationrequest;
    String   searchcode;
    String[] courseids = new String[0];
    String[] titles    = new String[0];

    public boolean isError()
    {
      return error;
    }

    public String getErrormessage()
    {
      return errormessage;
    }

    public String[] getTitles()
    {
      return titles;
    }
    
    public String[] getCourseids()
    {
      return courseids;
    }
  }

}
