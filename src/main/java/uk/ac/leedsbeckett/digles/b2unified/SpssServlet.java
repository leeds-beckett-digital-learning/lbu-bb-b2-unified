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

import blackboard.data.ValidationException;
import blackboard.persist.PersistenceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
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
public class SpssServlet extends HttpServlet
{
  WebAppCore webappcore;
  
  @Override
  public void init( ServletConfig config ) throws ServletException
  {
    super.init( config );
    webappcore = WebAppCore.getInstance( config.getServletContext() );
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
    
    r.error = false;
    r.requestedpackage = req.getParameter( "package" );
    try
    {
      SpssOutcomes outcomes = new SpssOutcomes();
      process( outcomes, r );
    }
    catch ( Exception e )
    {
      webappcore.logger.error( "Exception while trying to enrol user", e );
      r.error = true;
      r.errormessage = "Technical fault attempting to process request.";
    }
    
    resp.setContentType( "application/json" );
    ServletOutputStream out = resp.getOutputStream();
    mapper.writeValue( out, r );
  }

  public void process( SpssOutcomes outcomes, ResponsePOJO r ) throws PersistenceException, ValidationException, NoSuchAlgorithmException, MessagingException
  {
    if ( outcomes.hasFailed() )
    {
      r.error = true;
      r.errormessage = outcomes.errormessage;
      return;
    }
    
    r.requestreference = outcomes.getRequestReference();
    r.downloadurl = outcomes.getDownloadUrl( r.requestedpackage );
    webappcore.logger.debug( "Download URL " + r.downloadurl );
    sendDownloadLinkToUser( outcomes, r );
  }

  
  public void sendDownloadLinkToUser( SpssOutcomes outcomes, ResponsePOJO r ) throws PersistenceException, ValidationException, AddressException, MessagingException
  {
    String content = outcomes.getEmailContent( r.requestedpackage );
    String subject = "SPSS Software Download Request (" + outcomes.getRequestReference() + ")";
    String address = outcomes.getUserEmail();
    String admin   = webappcore.configproperties.getSpssReceiptEmail();
    String from    = webappcore.configproperties.getEmail();
    
    webappcore.logger.debug( "Send email with subject " + subject );
    webappcore.logger.debug( "To " + address + " and " + admin );
    webappcore.logger.debug( content );
    
    // Temporarily bodge email addresses for development
    // address = "j.r.maber@leedsbeckett.ac.uk";
    // admin = "digitallearning@leedsbeckett.ac.uk";
    
    
    InternetAddress[] recipients = { new InternetAddress( address ) };
    InternetAddress[] cclist     = { new InternetAddress( admin ), new InternetAddress( from ) };
    InternetAddress fromia       = new InternetAddress( from );
    
    webappcore.sendPlainEmail( subject, fromia, null, recipients, cclist, content );
  } 
  
  public class ResponsePOJO
  {
    boolean  error;
    String   errormessage;
    String   requestedpackage;
    String   requestreference;
    String   downloadurl;

    public boolean isError()
    {
      return error;
    }

    public String getErrormessage()
    {
      return errormessage;
    }

    public String getRequestedpackage()
    {
      return requestedpackage;
    }

    public String getRequestreference()
    {
      return requestreference;
    }

    public String getDownloadurl()
    {
      return downloadurl;
    }
  }

}
