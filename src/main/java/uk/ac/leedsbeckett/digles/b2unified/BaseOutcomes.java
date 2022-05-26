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
import java.util.Random;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author maber01
 */
public class BaseOutcomes
{
  static final Object o = new Object();
  static final Random random = new Random( System.currentTimeMillis() );
  static String styletemplate=null;
  
  final String id;
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
  String stylecontent=null;
  
  
  
  public BaseOutcomes()
  {
    id = Long.toHexString( random.nextLong() );
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
      setStyleContent();    
    }
    catch ( Exception ex )
    {
      errormessage = "There was a technical problem with this panel.";
      if ( webappcore != null )
          webappcore.logger.error( errormessage, ex );
    }
  }

  private void setStyleContent()
  {
    synchronized ( o )
    {
      if ( styletemplate == null )
      {
        try
        {
          webappcore.logger.debug( BaseOutcomes.class.getClassLoader().getResource( "/css/dynamic.css" ) );
          styletemplate = new String( BaseOutcomes.class.getClassLoader().getResourceAsStream( "/css/dynamic.css" ).readAllBytes(), "UTF-8" );
          webappcore.logger.debug( styletemplate );
        }
        catch ( Exception e )
        {
          webappcore.logger.error(  "Unable to load CSS.", e );
          styletemplate = "/* error loading */";
        }
      }
    }    
  }  

  public String getId()
  {
    return id;
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

  public String getStyleColour()
  {
    return "pink";
  }
  
  public String getStyleContent()
  {
    
    synchronized( this )
    {
      if ( stylecontent == null )
      {
        webappcore.logger.debug( "Selected variant = " + this.getStyleColour() );
        String fcolor1 = "#ffffff";
        String bcolor1 = "#000000";
        String fcolor2 = "#000000";
        String bcolor2 = "#ffffff";
        String bcolor3 = "#777777";
        if ( "pink".equals( this.getStyleColour() ) )
        {
          fcolor1 = "#ffffff";
          bcolor1 = "#690039";
          fcolor2 = "#690039";
          bcolor2 = "#f9cbdf";
          bcolor3 = "#e382b5";
        }

        String s = styletemplate;
        s = s.replace( "__id__",      getId() );
        s = s.replace( "__bcolor1__", bcolor1 );
        s = s.replace( "__fcolor1__", fcolor1 );
        s = s.replace( "__bcolor2__", bcolor2 );
        s = s.replace( "__fcolor2__", fcolor2 );
        s = s.replace( "__bcolor3__", bcolor3 );

        stylecontent = s;
        webappcore.logger.debug( stylecontent );
      }
    }
    
    return stylecontent;
  }  
}
