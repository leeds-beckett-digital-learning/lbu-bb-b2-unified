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

import blackboard.platform.plugin.PlugInUtil;
import blackboard.portal.data.ExtraInfo;
import blackboard.portal.data.Module;

/**
 *
 * @author maber01
 */
public class SelfEnrolOutcomes extends BaseOutcomes
{
  Module module;  // blackboard plugin module, not course module
  String extref;
  ExtraInfo extrainfo;
  boolean coursemode;
          
  public SelfEnrolOutcomes()
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
      
      module         = (Module)request.getAttribute( "blackboard.portal.data.Module" );
      extref         = module.getExtRef();
      webappcore.logger.debug( "SelfEnrolOutcomes : ExtRef = " + extref );
      if ( extref == null || (!extref.contains( "course" ) && !extref.contains( "org" ) ) )
        throw new IllegalArgumentException( "Plugin module reference must contain either 'course' or 'org'." );
      coursemode = extref.contains( "course" );
    }
    catch ( Exception ex )
    {
      errormessage = "There was a technical problem with this panel.";
      if ( webappcore != null )
          webappcore.logger.error( errormessage, ex );
    }
  }

  public boolean isCoursemode()
  {
    return coursemode;
  }
  
  public boolean hasFailed()
  {
    return errormessage != null;
  }
  
  public String getErrorMessage()
  {
    return errormessage;  
  }

  public String getSearchEndpointURL()
  {
    if ( coursemode )
      return PlugInUtil.getUri("LBU", "bbb2unified", "servlets/selfenrolsearch/course/");
    return PlugInUtil.getUri("LBU", "bbb2unified", "servlets/selfenrolsearch/organization/");
  }
  
  public String getEnrolEndpointURL()
  {
    if ( coursemode )
      return PlugInUtil.getUri("LBU", "bbb2unified", "servlets/selfenrolenrol/course/");
    return PlugInUtil.getUri("LBU", "bbb2unified", "servlets/selfenrolenrol/organization/");
  }
  
  @Override
  public String getStyleColour()
  {
    if ( coursemode )
      return webappcore.configproperties.getCourseSelfEnrolColourScheme();
    return webappcore.configproperties.getOrganizationSelfEnrolColourScheme();
  }
  
  public String getTitle()
  {
    if ( coursemode )
      return webappcore.configproperties.getCourseSelfEnrolTitle();
    return webappcore.configproperties.getOrganizationSelfEnrolTitle();
  }
  
  public String getButtonLabel()
  {
    if ( coursemode )
      return webappcore.configproperties.getCourseSelfEnrolButtonLabel();
    return webappcore.configproperties.getOrganizationSelfEnrolButtonLabel();
  }
  public String getInfoText()
  {
    if ( coursemode )
      return webappcore.configproperties.getCourseSelfEnrolInfoHtml().getFormattedText();
    return webappcore.configproperties.getOrganizationSelfEnrolInfoHtml().getFormattedText();
  }

  public String getHelpText()
  {
    if ( coursemode )
      return webappcore.configproperties.getCourseSelfEnrolHelpHtml().getFormattedText();
    return webappcore.configproperties.getOrganizationSelfEnrolHelpHtml().getFormattedText();
  } 
}
