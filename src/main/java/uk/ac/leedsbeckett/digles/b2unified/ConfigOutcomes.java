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
import blackboard.platform.security.SecurityUtil;
import org.apache.log4j.Level;

/**
 *
 * @author jon
 */
public final class ConfigOutcomes extends BaseOutcomes
{
  enum Display
  {
    FORM, CONFIRM, ERROR
  }
  
  
  String  course_id;
  String  action;
  Display display_type = Display.ERROR;
  String  error_message;
  
  
  public ConfigOutcomes()
  {
    super();
    if ( errormessage != null ) return;
    
    if ( !SecurityUtil.userHasAllEntitlements( new String[] { "system.administration.top.VIEW" } ) )
    {
      error_message = "User not allowed access to configure this building block. Requires access to sysadmin page.";
      return;
    }
    
    
    webappcore.logger.info( "ConfigHelper.processConfigPage()" );

    course_id = request.getParameter("course_id");
    action = request.getParameter( "action" );
    if ( action == null || action.length() == 0 )
      action = "displayform";    
    webappcore.logger.info( "action = " + action );    
    
    if ( "displayform".equals(action) )
    {
      setDisplayForm();
    }
    else if ( "config".equals(action) )
    {
      try
      {
        settings.setLogLevel( Level.toLevel( request.getParameter( "loglevel"      )      ) );        
        settings.setEmail(                   request.getParameter( "email"                ) );
        settings.setHelpWebsite(             request.getParameter( "helpwebsite"          ) );
        settings.setInstitutionName(         request.getParameter( "institutionname"      ) );
        
        settings.setStudentRoleId(           request.getParameter( "studentroleid"        ) );
        settings.setStaffRoleId(             request.getParameter( "staffroleid"          ) );        

        settings.setProspectusUrl(           request.getParameter( "prospectusurl"        ) );
        settings.setProspectusSecurityToken( request.getParameter( "prospectussecuritytoken" ) );
        
        settings.setTrainingCoursePrefix(    request.getParameter( "trainingcourseprefix" ) );
        settings.setTrainingTitle(           request.getParameter( "trainingtitle"        ) );
        settings.setTrainingButtonLabel(     request.getParameter( "trainingbuttonlabel"  ) );
        settings.setTrainingInfoHtml(        request.getParameter( "traininginfohtmltext" ) );
        settings.setTrainingHelpHtml(        request.getParameter( "traininghelphtmltext" ) );
        settings.setTrainingColourScheme(    request.getParameter( "trainingcolourscheme" ) );
        settings.setSandboxTitle(            request.getParameter( "sandboxtitle"        ) );
        settings.setSandboxButtonLabel(      request.getParameter( "sandboxbuttonlabel"  ) );
        settings.setSandboxInfoHtml(         request.getParameter( "sandboxinfohtmltext" ) );
        settings.setSandboxHelpHtml(         request.getParameter( "sandboxhelphtmltext" ) );
        settings.setSandboxColourScheme(     request.getParameter( "sandboxcolourscheme" ) );
        settings.setCourseSelfEnrolTitle(               request.getParameter( "courseselfenroltitle"        ) );
        settings.setCourseSelfEnrolButtonLabel(         request.getParameter( "courseselfenrolbuttonlabel"  ) );
        settings.setCourseSelfEnrolInfoHtml(            request.getParameter( "courseselfenrolinfohtmltext" ) );
        settings.setCourseSelfEnrolHelpHtml(            request.getParameter( "courseselfenrolhelphtmltext" ) );
        settings.setCourseSelfEnrolColourScheme(        request.getParameter( "courseselfenrolcolourscheme" ) );
        settings.setCourseSelfEnrolReceiptEmail(        request.getParameter( "courseselfenrolemailtexttext"      ) );
        settings.setCourseSelfEnrolValidation(          request.getParameter( "courseselfenrolvalidation"         ) );
        settings.setCourseSelfEnrolFilter(              request.getParameter( "courseselfenrolfilter"             ) );
        settings.setOrganizationSelfEnrolTitle(         request.getParameter( "organizationselfenroltitle"        ) );
        settings.setOrganizationSelfEnrolButtonLabel(   request.getParameter( "organizationselfenrolbuttonlabel"  ) );
        settings.setOrganizationSelfEnrolInfoHtml(      request.getParameter( "organizationselfenrolinfohtmltext" ) );
        settings.setOrganizationSelfEnrolHelpHtml(      request.getParameter( "organizationselfenrolhelphtmltext" ) );
        settings.setOrganizationSelfEnrolColourScheme(  request.getParameter( "organizationselfenrolcolourscheme" ) );
        settings.setOrganizationSelfEnrolReceiptEmail(  request.getParameter( "organizationselfenrolemailtexttext" ) );
        settings.setOrganizationSelfEnrolValidation(    request.getParameter( "organizationselfenrolvalidation"    ) );
        settings.setOrganizationSelfEnrolFilter(        request.getParameter( "organizationselfenrolfilter"        ) );
        settings.setSpssTitle(                          request.getParameter( "spsstitle"                  ) );
        settings.setSpssButtonLabel(                    request.getParameter( "spssbuttonlabel"            ) );
        settings.setSpssInfoHtml(                       request.getParameter( "spssinfohtmltext"           ) );
        settings.setSpssHelpHtml(                       request.getParameter( "spsshelphtmltext"           ) );
        settings.setSpssFormHtml(                       request.getParameter( "spssformhtmltext"           ) );
        settings.setSpssColourScheme(                   request.getParameter( "spsscolourscheme"           ) );
        settings.setSpssLicenseCode(                    request.getParameter( "spsslicensecode"            ) );
        settings.setSpssLicenseCodeAmos(                request.getParameter( "spsslicensecodeamos"        ) );
        settings.setSpssDownloadUrlWindows(             request.getParameter( "spssdownloadurlwindows"     ) );
        settings.setSpssDownloadUrlMac(                 request.getParameter( "spssdownloadurlmac"         ) );
        settings.setSpssDownloadUrlAmos(                request.getParameter( "spssdownloadurlamos"        ) );
        settings.setSpssInstructionsUrlWindows(         request.getParameter( "spssinstructionsurlwindows" ) );
        settings.setSpssInstructionsUrlMac(             request.getParameter( "spssinstructionsurlmac"     ) );
        settings.setSpssInstructionsUrlAmos(            request.getParameter( "spssinstructionsurlamos"    ) );
        settings.setSpssReceiptEmail(                   request.getParameter( "spssreceiptemail"           ) );
        settings.setSpssTerms(               stripTags( request.getParameter( "spsstermstext"            ) ) );
        settings.setSpssEmailText(                      request.getParameter( "spssemailtexttext"          ) );
        webappcore.saveProperties();
        setDisplayConfirm();
      }
      catch ( Exception ex )
      {
        webappcore.logger.error( "Unable to create content.", ex );
        setDisplayError();
        error_message = "Technical fault prevented creation of item: " + ex.toString();
      }
    }
    else
    {
      setDisplayError();
      error_message = "Unknown action: " + action;      
    }

    webappcore.logger.info( "display = " + display_type.name() );
  }  
  
  private String stripTags( String tagged )
  {
    StringBuilder sb = new StringBuilder( tagged.length() );
    
    boolean intag=false;
    for ( int i=0; i<tagged.length(); i++ )
    {
      char c = tagged.charAt( i );
      if ( intag )
      {
        if ( c == '>' )
          intag = false;
      }
      else
      {
        if ( c == '<' )
          intag = true;
        else
          sb.append( c );
      }
    }
    
    return sb.toString();
  }

  public boolean isDisplayForm()   { return display_type == Display.FORM;  }
  public boolean isDisplayConfirm(){ return display_type == Display.CONFIRM;  }
  public boolean isDisplayError()  { return display_type == Display.ERROR; }  
  
  void setDisplayForm()     { display_type = Display.FORM;  }
  void setDisplayConfirm()  { display_type = Display.CONFIRM;  }
  void setDisplayError()    { display_type = Display.ERROR; }  

  public String isLogLevelSelected( String strlevel )
  {
    Level level = Level.toLevel( strlevel );
    return level.equals( settings.getLogLevel() )?"true":"false";
  }
    
  public String isTrainingColourSchemeSelected( String strcolour )
  {
    return strcolour.equals( settings.getTrainingColourScheme() )?"true":"false";
  }
  
  public String isSandboxColourSchemeSelected( String strcolour )
  {
    return strcolour.equals( settings.getSandboxColourScheme() )?"true":"false";
  }
  
  public String isCourseSelfEnrolColourSchemeSelected( String strcolour )
  {
    return strcolour.equals( settings.getCourseSelfEnrolColourScheme() )?"true":"false";
  }
  
  public String isOrganizationSelfEnrolColourSchemeSelected( String strcolour )
  {
    return strcolour.equals( settings.getOrganizationSelfEnrolColourScheme() )?"true":"false";
  }
  
  public String isSpssColourSchemeSelected( String strcolour )
  {
    return strcolour.equals( settings.getSpssColourScheme() )?"true":"false";
  }
  
  public UnifiedBuildingBlockProperties getSettings()
  {
    return this.settings;
  }
  
  public String getErrorMessage()
  {
    return this.error_message;
  }
}
