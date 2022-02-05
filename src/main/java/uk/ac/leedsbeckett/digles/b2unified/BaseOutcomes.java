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

import blackboard.data.user.User;
import blackboard.persist.user.UserDbLoader;
import blackboard.platform.context.Context;
import blackboard.platform.context.ContextManagerFactory;
import blackboard.platform.session.BbSession;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author maber01
 */
public class BaseOutcomes
{
  String errormessage = null;
  WebAppCore webappcore;
  UnifiedBuildingBlockProperties settings;
  Context bbcontext;
  HttpServletRequest request;
  ServletContext servletcontext;
  BbSession bbSession;
  User user;
  boolean student=false;
  boolean staff=false;

  public BaseOutcomes()
  {
    try
    {
      bbcontext      = ContextManagerFactory.getInstance().getContext();
      request        = bbcontext.getRequest();
      servletcontext = request.getServletContext();
      webappcore     = WebAppCore.getInstance( servletcontext );
      settings       = webappcore.configproperties;
      bbSession      = bbcontext.getSession();
      UserDbLoader uLoader = UserDbLoader.Default.getInstance();
      user = uLoader.loadByUserName( bbSession.getUserName() );
      String roleid = user.getPortalRoleId().toExternalString();
      if ( roleid != null )
      {
        student = roleid.equals(  webappcore.configproperties.getStudentRoleId() );
        staff   = roleid.equals(  webappcore.configproperties.getStaffRoleId()   );
      }    
    }
    catch ( Exception ex )
    {
      errormessage = "There was a technical problem with this panel.";
      if ( webappcore != null )
          webappcore.logger.error( errormessage, ex );
    }
    
  }
  
  public String getName()
  {
    return user.getGivenName() + " " + user.getFamilyName();
  }
  
  public String getUserName()
  {
    return user.getUserName();
  }
  
  public String getUserId()
  {
    return user.getStudentId();
  }
  
  public String getUserEmail()
  {
    return user.getEmailAddress();
  }
  
  public String getUserCourseCode()
  {
    if ( staff )
      return "N/A";
    return user.getJobTitle();
  }
  
  public String getUserLevel()
  {
    if ( staff )
      return "Staff member.";
    
    String coursecode = user.getJobTitle();
    if ( coursecode == null ) return "";
    String[] parts = coursecode.split( "[|]" );
    if ( parts == null || parts.length < 2 ) return "";
    String levelcode = parts[1];
    switch ( levelcode )
    {
      case "4":
      case "5":
      case "6":
        return "Undergraduate";
      case "7":
        return "Masters";
      case "8":
        return "Level 8";
    }
    return levelcode;
  }
  
  public String getUserCourse()
  {
    if ( staff )
      return "N/A";
    return user.getCompany();
  }
  
  public String getUserSchool()
  {
    return user.getDepartment();
  }
  
  public String getPortalRoleId()
  {
    return user.getPortalRoleId().getExternalString();
  }
  
}
