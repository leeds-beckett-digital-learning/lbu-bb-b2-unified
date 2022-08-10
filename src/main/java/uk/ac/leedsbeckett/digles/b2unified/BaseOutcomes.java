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
    id = "id" + Long.toHexString( random.nextLong() );
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

  public String getStyleSheetHref()
  {
    return "'" + request.getServletContext().getContextPath() + "/style/unified.css'";
  }
  
  public String getId()
  {
    return id;
  }
  
  public String toId( String name )
  {
    return id + name;
  }
  
  public String toIdA( String name )
  {
    return "id=" + id + name;
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
    return "grey";
  }
  
  static String[][][] colours    = 
  {
    {
      { "grey" },
      { "rgb(47, 50, 51)", "rgb(113, 115, 115)", "rgb(199, 196, 195)", "white" },
      { "white", "rgb(47, 50, 51)", "black" },
      { "rgb(47, 50, 51)", "rgb(113, 115, 115)" }
    },

    {
      { "blue" },
      { "rgb(0, 64, 91)", "rgb(0, 183, 232)", "rgb(199, 228, 244)", "white" },
      { "white", "rgb(0, 64, 91)", "black" },
      { "rgb(0, 64, 91)", "rgb(0, 183, 232)" }
    },

    {
      { "green" },
      { "rgb(28, 68, 4)", "rgb(62, 181, 73)", "rgb(210, 232, 202)", "white" },
      { "white", "rgb(28, 68, 4)", "black" },
      { "rgb(28, 68, 4)", "rgb(62, 181, 73)" }
    },

    {
      { "lime" },
      { "rgb(28, 68, 4)", "rgb(141, 198, 63)", "rgb(230, 240, 203)", "white" },
      { "white", "rgb(28, 68, 4)", "black" },
      { "rgb(28, 68, 4)", "rgb(141, 198, 63)" }
    },

    {
      { "orange" },
      { "rgb(85, 37, 0)", "rgb(245, 130, 32)", "rgb(254, 226, 201)", "white" },
      { "white", "rgb(85, 37, 0)", "black" },
      { "rgb(85, 37, 0)", "rgb(245, 130, 32)" }
    },

    {
      { "pink" },
      { "rgb(105, 0, 57)", "rgb(227, 130, 181)", "rgb(249, 203, 223)", "white" },
      { "white", "rgb(105, 0, 57)", "black" },
      { "rgb(105, 0, 57)", "rgb(171, 74, 156)" }
    },

    {
      { "purple" },
      { "rgb(39, 7, 88)", "rgb(140, 118, 176)", "rgb(211, 191, 221)", "white" },
      { "white", "rgb(39, 7, 88)", "black" },
      { "rgb(39, 7, 88)", "rgb(171, 74, 156)" }
    },

    {
      { "red" },
      { "rgb(104, 0, 0)", "rgb(238, 49, 42)", "rgb(253, 215, 198)", "white" },
      { "white", "rgb(104, 0, 0)", "black" },
      { "rgb(104, 0, 0)", "rgb(238, 49, 42)" }
    },

    {
      { "teal" },
      { "rgb(0, 68, 65)", "rgb(24, 186, 171)", "rgb(176, 223, 219)", "white" },
      { "white", "rgb(0, 68, 65)", "black" },
      { "rgb(0, 68, 65)", "rgb(24, 186, 171)" }
    },

    {
      { "turq" },
      { "rgb(0, 69, 87)", "rgb(92, 199, 211)", "rgb(197, 233, 243)", "white" },
      { "white", "rgb(0, 69, 87)", "black" },
      { "rgb(0, 69, 87)", "rgb(92, 199, 211)" }
    },

    {
      { "violet" },
      { "rgb(89, 25, 78)", "rgb(171, 74, 156)", "rgb(218, 202, 227)", "white" },
      { "white", "rgb(89, 25, 78)", "black" },
      { "rgb(89, 25, 78)", "rgb(171, 74, 156)" }
    },

    {
      { "yellow" },
      { "rgb(71, 52, 0)", "rgb(228, 190, 20)", "rgb(255, 233, 194)", "white" },
      { "white", "rgb(71, 52, 0)", "black" },
      { "rgb(71, 52, 0)", "rgb(228, 190, 20)" }
    }
  };
        
  public String getStyleColour( String type, int n )
  {
    int t;
    if ( "bg".equals( type ) )
      t=1;
    else if ( "fg".equals( type ) )
      t=2;
    else if ( "bor".equals( type ) )
      t=3;
    else
      return "#ffffff";
     
    for ( int set=0; set<colours.length; set++ )
      if ( colours[set][0][0].equals( this.getStyleColour() ) )
      {
        if ( n<0 || n>= colours[set][t].length )
          return colours[set][t][0];
        return colours[set][t][n];
      }
    
    if ( n<0 || n>= colours[0][t].length )
      return colours[0][t][0];
    return colours[0][t][n];
  }  
}
