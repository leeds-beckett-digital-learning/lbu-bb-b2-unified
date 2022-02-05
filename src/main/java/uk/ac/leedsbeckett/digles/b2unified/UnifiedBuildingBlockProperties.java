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

import blackboard.base.FormattedText;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.regex.Pattern;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author jon
 */
public final class UnifiedBuildingBlockProperties extends Properties
{
  Logger logger;

  Pattern csearchpattern = null;  
  Pattern osearchpattern = null;
  
  Pattern cpattern = null;
  Pattern opattern = null;
  
  public UnifiedBuildingBlockProperties( Logger logger )
  {
    this.logger = logger;
  }

  @Override
  public synchronized void loadFromXML( InputStream in ) throws IOException, InvalidPropertiesFormatException
  {
    super.loadFromXML( in ); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    csearchpattern = null;
    osearchpattern = null;  
    cpattern = null;
    opattern = null;
  }

  @Override
  public synchronized void load( InputStream inStream ) throws IOException
  {
    super.load( inStream ); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
  }

  @Override
  public synchronized void load( Reader reader ) throws IOException
  {
    super.load( reader ); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
  }
  
  
  
  public Level getLogLevel()
  {
    return Level.toLevel( getProperty( "loglevel", "info" ) );
  }
  
  public void setLogLevel( Level level )
  {
    setProperty( "loglevel", level.toString() );
  }
  
  public String getEmail()
  {
    return getProperty( "email", "" );
  }
  
  public void setEmail( String email )
  {
    setProperty( "email", email );
  }
  
  public String getHelpWebsite()
  {
    return getProperty( "helpwebsite", "" );
  }
  
  public void setHelpWebsite( String helpwebsite )
  {
    setProperty( "helpwebsite", helpwebsite );
  }

  public String getInstitutionName()
  {
    return getProperty( "institutionname", "" );
  }
  public void setInstitutionName( String institutionname )
  {
    setProperty( "institutionname", institutionname );
  }
  
  
  
  public String getStudentRoleId()
  {
    return getProperty( "studentroleid", "" );
  }
  public void setStudentRoleId( String studentroleid )
  {
    setProperty( "studentroleid", studentroleid );
  }
  
  public String getStaffRoleId()
  {
    return getProperty( "staffroleid", "" );
  }
  public void setStaffRoleId( String staffroleid )
  {
    setProperty( "staffroleid", staffroleid );
  }
  
  public String getProspectusUrl()
  {
    return getProperty( "prospectusurl", "" );    
  }
  public void setProspectusUrl( String url )
  {
    setProperty( "prospectusurl", url );
  }
  
  public String getProspectusSecurityToken()
  {
    return getProperty( "prospectussecuritytoken", "" );    
  }
  public void setProspectusSecurityToken( String token )
  {
    setProperty( "prospectussecuritytoken", token );
  }
  
  
  public String getTrainingTitle()
  {
    return getProperty( "trainingtitle", "MyBeckett Modules for Self Paced Training" );
  }
  public void setTrainingTitle( String trainingtitle )
  {
    setProperty( "trainingtitle", trainingtitle );
  }
  
  public String getTrainingCoursePrefix()
  {
    return getProperty( "trainingcourseprefix", "LSTRN" );
  }
  public void setTrainingCoursePrefix( String trainingcourseprefix )
  {
    setProperty( "trainingcourseprefix", trainingcourseprefix );
  }
  
  public String getTrainingColourScheme()
  {
    return getProperty( "trainingcolourscheme", "green" );
  }
  public void setTrainingColourScheme( String trainingcolourscheme )
  {
    setProperty( "trainingcolourscheme", trainingcolourscheme );
  }
  
  public String getTrainingButtonLabel()
  {
    return getProperty( "trainingbuttonlabel", "Enrol" );
  }
  public void setTrainingButtonLabel( String trainingbuttonlabel )
  {
    setProperty( "trainingbuttonlabel", trainingbuttonlabel );
  }
    
  public FormattedText getTrainingInfoHtml()
  {
    return new FormattedText( getProperty( "traininginfohtml", "" ), FormattedText.Type.HTML );
  }
  public void setTrainingInfoHtml( String institutionname )
  {
    setProperty( "traininginfohtml", institutionname );
  }

  public FormattedText getTrainingHelpHtml()
  {
    return new FormattedText( getProperty( "traininghelphtml", "" ), FormattedText.Type.HTML );
  }
  public void setTrainingHelpHtml( String institutionname )
  {
    setProperty( "traininghelphtml", institutionname );
  }


  public String getSandboxTitle()
  {
    return getProperty( "sandboxtitle", "MyBeckett Sandbox for Self Paced Training" );
  }
  public void setSandboxTitle( String sandboxtitle )
  {
    setProperty( "sandboxtitle", sandboxtitle );
  }
  
  public String getSandboxColourScheme()
  {
    return getProperty( "sandboxcolourscheme", "green" );
  }
  public void setSandboxColourScheme( String sandboxcolourscheme )
  {
    setProperty( "sandboxcolourscheme", sandboxcolourscheme );
  }
  
  public String getSandboxButtonLabel()
  {
    return getProperty( "sandboxbuttonlabel", "Enrol" );
  }
  public void setSandboxButtonLabel( String sandboxbuttonlabel )
  {
    setProperty( "sandboxbuttonlabel", sandboxbuttonlabel );
  }
    
  public FormattedText getSandboxInfoHtml()
  {
    return new FormattedText( getProperty( "sandboxinfohtml", "" ), FormattedText.Type.HTML );
  }
  public void setSandboxInfoHtml( String sandboxinfohtml )
  {
    setProperty( "sandboxinfohtml", sandboxinfohtml );
  }

  public FormattedText getSandboxHelpHtml()
  {
    return new FormattedText( getProperty( "sandboxhelphtml", "" ), FormattedText.Type.HTML );
  }
  public void setSandboxHelpHtml( String sandboxhelphtml )
  {
    setProperty( "sandboxhelphtml", sandboxhelphtml );
  }


  public String getCourseSelfEnrolTitle()
  {
    return getProperty( "courseselfenroltitle", "MyBeckett Module Self Enrol" );
  }
  public void setCourseSelfEnrolTitle( String courseselfenroltitle )
  {
    setProperty( "courseselfenroltitle", courseselfenroltitle );
  }
  
  public String getCourseSelfEnrolColourScheme()
  {
    return getProperty( "courseselfenrolcolourscheme", "green" );
  }
  public void setCourseSelfEnrolColourScheme( String courseselfenrolcolourscheme )
  {
    setProperty( "courseselfenrolcolourscheme", courseselfenrolcolourscheme );
  }
  
  public String getCourseSelfEnrolButtonLabel()
  {
    return getProperty( "courseselfenrolbuttonlabel", "Enrol" );
  }
  public void setCourseSelfEnrolButtonLabel( String courseselfenrolbuttonlabel )
  {
    setProperty( "courseselfenrolbuttonlabel", courseselfenrolbuttonlabel );
  }
    
  public FormattedText getCourseSelfEnrolInfoHtml()
  {
    return new FormattedText( getProperty( "courseselfenrolinfohtml", "" ), FormattedText.Type.HTML );
  }
  public void setCourseSelfEnrolInfoHtml( String courseselfenrolinfohtml )
  {
    setProperty( "courseselfenrolinfohtml", courseselfenrolinfohtml );
  }

  public FormattedText getCourseSelfEnrolHelpHtml()
  {
    return new FormattedText( getProperty( "courseselfenrolhelphtml", "" ), FormattedText.Type.HTML );
  }
  public void setCourseSelfEnrolHelpHtml( String courseselfenrolhelphtml )
  {
    setProperty( "courseselfenrolhelphtml", courseselfenrolhelphtml );
  }

  public String getCourseSelfEnrolReceiptEmail()
  {
    return getProperty( "courseselfenrolreceiptemail", "" );
  }
  public void setCourseSelfEnrolReceiptEmail( String value )
  {
    setProperty( "courseselfenrolreceiptemail", value );
  }

  public String getCourseSelfEnrolValidation()
  {
    return getProperty( "courseselfenrolvalidation", "\\A\\d{3,6}+\\z" );
  }
  public void setCourseSelfEnrolValidation( String courseselfenrolvalidation )
  {
    setProperty( "courseselfenrolvalidation", courseselfenrolvalidation );
    csearchpattern = null;
  }
  public Pattern getCourseSelfEnrolValidationPattern()
  {
    if ( csearchpattern == null && getCourseSelfEnrolValidation() != null )
      csearchpattern = Pattern.compile(  getCourseSelfEnrolValidation() );
    return csearchpattern;
  }

  public String getCourseSelfEnrolFilter()
  {
    return getProperty( "courseselfenrolfilter", "\\A\\d+-((\\d{4,}+)|(PERM))\\z" );
  }
  public void setCourseSelfEnrolFilter( String courseselfenrolfilter )
  {
    setProperty( "courseselfenrolfilter", courseselfenrolfilter );
    cpattern = null;
  }
  public Pattern getCourseSelfEnrolFilterPattern()
  {
    if ( cpattern == null && getCourseSelfEnrolFilter() != null )
      cpattern = Pattern.compile(  getCourseSelfEnrolFilter() );
    return cpattern;
  }

  public String getOrganizationSelfEnrolTitle()
  {
    return getProperty( "organizationselfenroltitle", "MyBeckett Module Self Enrol" );
  }
  public void setOrganizationSelfEnrolTitle( String organizationselfenroltitle )
  {
    setProperty( "organizationselfenroltitle", organizationselfenroltitle );
  }
  
  public String getOrganizationSelfEnrolColourScheme()
  {
    return getProperty( "organizationselfenrolcolourscheme", "green" );
  }
  public void setOrganizationSelfEnrolColourScheme( String organizationselfenrolcolourscheme )
  {
    setProperty( "organizationselfenrolcolourscheme", organizationselfenrolcolourscheme );
  }
  
  public String getOrganizationSelfEnrolButtonLabel()
  {
    return getProperty( "organizationselfenrolbuttonlabel", "Enrol" );
  }
  public void setOrganizationSelfEnrolButtonLabel( String organizationselfenrolbuttonlabel )
  {
    setProperty( "organizationselfenrolbuttonlabel", organizationselfenrolbuttonlabel );
  }
    
  public FormattedText getOrganizationSelfEnrolInfoHtml()
  {
    return new FormattedText( getProperty( "organizationselfenrolinfohtml", "" ), FormattedText.Type.HTML );
  }
  public void setOrganizationSelfEnrolInfoHtml( String organizationselfenrolinfohtml )
  {
    setProperty( "organizationselfenrolinfohtml", organizationselfenrolinfohtml );
  }

  public FormattedText getOrganizationSelfEnrolHelpHtml()
  {
    return new FormattedText( getProperty( "organizationselfenrolhelphtml", "" ), FormattedText.Type.HTML );
  }
  public void setOrganizationSelfEnrolHelpHtml( String organizationselfenrolhelphtml )
  {
    setProperty( "organizationselfenrolhelphtml", organizationselfenrolhelphtml );
  }

  public String getOrganizationSelfEnrolReceiptEmail()
  {
    return getProperty( "organizationselfenrolreceiptemail", "" );
  }
  public void setOrganizationSelfEnrolReceiptEmail( String value )
  {
    setProperty( "organizationselfenrolreceiptemail", value );
  }

  public String getOrganizationSelfEnrolValidation()
  {
    return getProperty( "organizationselfenrolvalidation", "\\A\\w{3,6}+\\z" );
  }
  public void setOrganizationSelfEnrolValidation( String organizationselfenrolvalidation )
  {
    setProperty( "organizationselfenrolvalidation", organizationselfenrolvalidation );
    osearchpattern = null;
  }
  public Pattern getOrganizationSelfEnrolValidationPattern()
  {
    if ( osearchpattern == null && getOrganizationSelfEnrolValidation() != null )
      osearchpattern = Pattern.compile(  getOrganizationSelfEnrolValidation() );
    return osearchpattern;
  }

  public String getOrganizationSelfEnrolFilter()
  {
    return getProperty( "organizationselfenrolfilter", "\\A\\w+-\\w+\\z" );
  }
  public void setOrganizationSelfEnrolFilter( String organizationselfenrolfilter )
  {
    setProperty( "organizationselfenrolfilter", organizationselfenrolfilter );
    opattern = null;
  }
  public Pattern getOrganizationSelfEnrolFilterPattern()
  {
    if ( opattern == null && getOrganizationSelfEnrolFilter() != null )
      opattern = Pattern.compile(  getOrganizationSelfEnrolFilter() );
    return opattern;
  }

  public String getSpssTitle()
  {
    return getProperty( "spsstitle", "MyBeckett SPSS Software Request" );
  }
  public void setSpssTitle( String spsstitle )
  {
    setProperty( "spsstitle", spsstitle );
  }
  
  public String getSpssColourScheme()
  {
    return getProperty( "spsscolourscheme", "green" );
  }
  public void setSpssColourScheme( String spsscolourscheme )
  {
    setProperty( "spsscolourscheme", spsscolourscheme );
  }
  
  public String getSpssButtonLabel()
  {
    return getProperty( "spssbuttonlabel", "Enrol" );
  }
  public void setSpssButtonLabel( String spssbuttonlabel )
  {
    setProperty( "spssbuttonlabel", spssbuttonlabel );
  }
    
  public FormattedText getSpssInfoHtml()
  {
    return new FormattedText( getProperty( "spssinfohtml", "" ), FormattedText.Type.HTML );
  }
  public void setSpssInfoHtml( String spssinfohtml )
  {
    setProperty( "spssinfohtml", spssinfohtml );
  }

  public FormattedText getSpssHelpHtml()
  {
    return new FormattedText( getProperty( "spsshelphtml", "" ), FormattedText.Type.HTML );
  }
  public void setSpssHelpHtml( String spsshelphtml )
  {
    setProperty( "spsshelphtml", spsshelphtml );
  }

  public FormattedText getSpssFormHtml()
  {
    return new FormattedText( getProperty( "spssformhtml", "" ), FormattedText.Type.HTML );
  }
  public void setSpssFormHtml( String spssformhtml )
  {
    setProperty( "spssformhtml", spssformhtml );
  }
  
  public String getSpssTerms()
  {
    return getProperty( "spssterms", "Insert terms here." );
  }
  public void setSpssTerms( String value )
  {
    setProperty( "spssterms", value );
  }
  
  public String getSpssEmailText()
  {
    return getProperty( "spssemailtext", "Insert text here." );
  }
  public void setSpssEmailText( String value )
  {
    setProperty( "spssemailtext", value );
  }
  
  public String getSpssLicenseCode()
  {
    return getProperty( "spsslicensecode", "" );
  }
  public void setSpssLicenseCode( String value )
  {
    setProperty( "spsslicensecode", value );
  }
    
  public String getSpssLicenseCodeAmos()
  {
    return getProperty( "spsslicensecodeamos", "" );
  }
  public void setSpssLicenseCodeAmos( String value )
  {
    setProperty( "spsslicensecodeamos", value );
  }
    

  public String getSpssDownloadUrlWindows()
  {
    return getProperty( "spssdownloadurlwindows", "" );
  }
  public void setSpssDownloadUrlWindows( String value )
  {
    setProperty( "spssdownloadurlwindows", value );
  }
    
  public String getSpssDownloadUrlMac()
  {
    return getProperty( "spssdownloadurlmac", "" );
  }
  public void setSpssDownloadUrlMac( String value )
  {
    setProperty( "spssdownloadurlmac", value );
  }
    
  public String getSpssDownloadUrlAmos()
  {
    return getProperty( "spssdownloadurlamos", "" );
  }
  public void setSpssDownloadUrlAmos( String value )
  {
    setProperty( "spssdownloadurlamos", value );
  }

  public String getSpssInstructionsUrlWindows()
  {
    return getProperty( "spssinstructionsurlwindows", "" );
  }
  public void setSpssInstructionsUrlWindows( String value )
  {
    setProperty( "spssinstructionsurlwindows", value );
  }
    
  public String getSpssInstructionsUrlMac()
  {
    return getProperty( "spssinstructionsurlmac", "" );
  }
  public void setSpssInstructionsUrlMac( String value )
  {
    setProperty( "spssinstructionsurlmac", value );
  }
    
  public String getSpssInstructionsUrlAmos()
  {
    return getProperty( "spssinstructionsurlamos", "" );
  }
  public void setSpssInstructionsUrlAmos( String value )
  {
    setProperty( "spssinstructionsurlamos", value );
  }
    
  public String getSpssReceiptEmail()
  {
    return getProperty( "spssreceiptemail", "" );
  }
  public void setSpssReceiptEmail( String value )
  {
    setProperty( "spssreceiptemail", value );
  }
    
  
}
