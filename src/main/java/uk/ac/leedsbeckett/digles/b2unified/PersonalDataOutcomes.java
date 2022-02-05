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

/**
 *
 * @author maber01
 */
public class PersonalDataOutcomes extends BaseOutcomes
{
  String course = null;
  String school = null;
  String coursecode = null;
  String level;
  String givenname = null;
  

  String coursetitle = "";
  String coursesubtitle = "";
  String stafftitle = "";
  String staffsubtitle = "";

  public PersonalDataOutcomes()
  {
    super();
    if ( errormessage != null ) return;
    
    try
    {
      givenname = user.getGivenName();
      if ( givenname == null ) givenname = "";
      course = user.getCompany();
      coursecode = user.getJobTitle();
      school = user.getDepartment();

      if ( school == null || school.isBlank() )
        coursetitle = "Leeds Beckett University";
      else
        coursetitle = school;

      if ( course == null || course.isBlank() )
        coursesubtitle = "Welcome to My Beckett";
      else
        coursesubtitle = course;

      stafftitle = "Staff MyBeckett Home Page";
      staffsubtitle = "Welcome " + givenname;
      
      if ( coursecode != null && coursecode.contains( "|" ) )
      {
        level = coursecode.substring( coursecode.indexOf( "|" ) + 1 );
        if ( !level.isBlank() && !level.contains( "0" ) )
          coursesubtitle = coursesubtitle + " - Level " + level;
      }
    }
    catch ( Exception ex )
    {
      errormessage = "There was a technical problem with this panel.";
      if ( webappcore != null )
        webappcore.logger.error( errormessage, ex );
    }
  }

  public boolean hasFailed()
  {
    return errormessage != null;
  }

  public String getErrorMessage()
  {
    return errormessage;
  }

  public String getCourseTitle()
  {
    return coursetitle;
  }

  public String getCourseSubTitle()
  {
    return coursesubtitle;
  }

  public String getStaffTitle()
  {
    return stafftitle;
  }

  public String getStaffSubTitle()
  {
    return staffsubtitle;
  }

}
