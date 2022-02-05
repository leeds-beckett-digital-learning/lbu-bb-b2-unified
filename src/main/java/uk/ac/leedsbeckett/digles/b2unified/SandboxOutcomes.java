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
import blackboard.platform.plugin.PlugInUtil;

/**
 *
 * @author maber01
 */
public class SandboxOutcomes extends BaseOutcomes
{
  String courseid;
  boolean exists;
  boolean enrolled;
  
  public SandboxOutcomes()
  {
    super();
    if ( errormessage != null ) return;
    
    try
    {
      if ( !staff )
      {
        errormessage = "This tool is only available to users whose primary role is staff.";
        return;
      }
      courseid = buildSandboxCourseID( webappcore.configproperties.getTrainingCoursePrefix(), user.getUserName() );
      Course course = Utils.getCourse( courseid );
      exists   = course != null;
      enrolled = exists && Utils.isMember( course, user );
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

  public String getEndpointURL()
  {
    return PlugInUtil.getUri("LBU", "bbb2unified", "servlets/createsandbox/");
  }
  
  public String getStyleColour()
  {
    return webappcore.configproperties.getSandboxColourScheme();
  }
  
  public String getTitle()
  {
    return webappcore.configproperties.getSandboxTitle();
  }
  
  public String getButtonLabel()
  {
    return webappcore.configproperties.getSandboxButtonLabel();
  }
  public String getInfoText()
  {
    return webappcore.configproperties.getSandboxInfoHtml().getFormattedText();
  }

  public String getHelpText()
  {
    return webappcore.configproperties.getSandboxHelpHtml().getFormattedText();
  }
  
  public boolean courseExistsAlready()
  {
    return exists;
  }
  
  public boolean enrolledAlready()
  {
    return enrolled;
  }
  
  public String getCourseID()
  {
    return courseid;
  }  
  
  public static final String buildSandboxCourseID( String prefix, String username )
  {
    StringBuilder courseid = new StringBuilder();
    courseid.append( "SANDBOX_" );
    courseid.append( prefix );
    courseid.append( "_" );
    courseid.append( username );
    return courseid.toString();
  }
}
