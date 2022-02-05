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
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author maber01
 */
public final class SpssOutcomes extends BaseOutcomes
{
  long timestamp = System.currentTimeMillis();
  String requestreference;
          
  public SpssOutcomes()
  {
    super();
    if ( errormessage != null ) return;
    
    try
    {
      if ( !student && !staff )
        errormessage = "This tool is only available to users whose primary role is either staff or student.";
      else if ( getUserId().length() == 0 )
        errormessage = "Student/staff ID is missing so cannot process request.";
      else if ( getUserEmail().length() == 0 )
        errormessage = "Your email address is missing so cannot process request.";  
      computeRequestReference();
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
    return PlugInUtil.getUri("LBU", "bbb2unified", "servlets/spss/");
  }
  
  public String getStyleColour()
  {
    return webappcore.configproperties.getSpssColourScheme();
  }
  
  public String getTitle()
  {
    return webappcore.configproperties.getSpssTitle();
  }
  
  public String getButtonLabel()
  {
    return webappcore.configproperties.getSpssButtonLabel();
  }
  public String getInfoText()
  {
    return webappcore.configproperties.getSpssInfoHtml().getFormattedText();
  }

  public String getHelpText()
  {
    return webappcore.configproperties.getSpssHelpHtml().getFormattedText();
  }  

  public String getFormTextAsJavascriptLiteral()
  {
    String s = webappcore.configproperties.getSpssFormHtml().getFormattedText();
    s = "`" + s.replaceAll( "`", "'" ) + "`"; 
    return s;
  }
  
  public String getTermsAsJavascriptLiteral()
  {
    String s = getTerms();
    s = "`" + s.replaceAll( "`", "'" ) + "`"; 
    return s;    
  }
        
  public String getTerms()
  {
    return webappcore.configproperties.getSpssTerms();
  }
  
  
  void computeRequestReference() throws NoSuchAlgorithmException
  {
    String DATE_FORMAT_NOW = "dd-MM-yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat( DATE_FORMAT_NOW );
    String timeToken = sdf.format( new Date( timestamp ) );
  
    MessageDigest md = MessageDigest.getInstance("MD5");
    String plainText = getUserId() + Long.toString( timestamp ); 
    md.update( plainText.getBytes() );
    byte[] digest = md.digest();
    BigInteger big = new BigInteger( 1, digest );
    String hexString = big.toString( 16 );
    String refRequest = hexString.substring(0,8); // turns the MD5 Hash into 8 chars reference code!
    requestreference = "LLI-SOFT-REQ_" + refRequest + "_" + timeToken;
  }

  public String getRequestReference()
  {
    return requestreference;
  }
  
  public String getDownloadUrl( String packageid )
  {
    webappcore.logger.debug( "getDownloadUrl( " + packageid + " )" );
    switch ( packageid )
    {
      case "spsswin":
        return webappcore.configproperties.getSpssDownloadUrlWindows();
      case "spssmac":
        return webappcore.configproperties.getSpssDownloadUrlMac();
      case "spssamoswin":
        return webappcore.configproperties.getSpssDownloadUrlAmos();
    }
    webappcore.logger.debug( "          Unknown package id." );
    return "";
  }

  public String getEmailContent( String packageid )
  {
    String template = webappcore.configproperties.getSpssEmailText();
    String content = template;
    String downloadlink="", installlink="", licensecode="", packagename="";
    switch ( packageid )
    {
      case "spsswin":
        packagename  = "SPSS for Windows";
        downloadlink = webappcore.configproperties.getSpssDownloadUrlWindows();
        installlink  = webappcore.configproperties.getSpssInstructionsUrlWindows();
        licensecode  = webappcore.configproperties.getSpssLicenseCode();
        break;
      case "spssmac":
        packagename  = "SPSS for Mac";
        downloadlink = webappcore.configproperties.getSpssDownloadUrlMac();
        installlink = webappcore.configproperties.getSpssInstructionsUrlMac();
        licensecode  = webappcore.configproperties.getSpssLicenseCode();
        break;
      case "spssamoswin":
        packagename  = "SPSS AMOS for Windows";
        downloadlink = webappcore.configproperties.getSpssDownloadUrlAmos();
        installlink = webappcore.configproperties.getSpssInstructionsUrlAmos();
        licensecode  = webappcore.configproperties.getSpssLicenseCodeAmos();
        break;
    }
    content = content.replaceAll( "\r", "" );
    content = content.replaceAll( "</p>", "\n" );  // End of para replaced with new line
    content = content.replaceAll( "<[^>]*>", "" ); // All other tags stripped out
    content = content.replaceAll( "&nbsp;", " " ); // nbsp Entity replaced with space.
    //content = content.replaceAll( "\n+", "\n" );
    content = content.replaceAll( "\\$\\{reference}",    getRequestReference() );
    content = content.replaceAll( "\\$\\{name}",         getName()             );
    content = content.replaceAll( "\\$\\{username}",     getUserName()         );
    content = content.replaceAll( "\\$\\{id}",           getUserId()           );
    content = content.replaceAll( "\\$\\{email}",        getUserEmail()        );
    content = content.replaceAll( "\\$\\{coursecode}",   getUserCourseCode()   );
    content = content.replaceAll( "\\$\\{course}",       getUserCourse()       );
    content = content.replaceAll( "\\$\\{level}",        getUserLevel()        );
    content = content.replaceAll( "\\$\\{school}",       getUserSchool()       );
    content = content.replaceAll( "\\$\\{role}",         getPortalRoleId()     );
    content = content.replaceAll( "\\$\\{package}",      packagename           );
    content = content.replaceAll( "\\$\\{downloadlink}", downloadlink          );
    content = content.replaceAll( "\\$\\{installlink}",  installlink           );
    content = content.replaceAll( "\\$\\{licensecode}",  licensecode           );
    content = content.replaceAll( "\\$\\{terms}",        getTerms()            );
    return content;
  }
}
