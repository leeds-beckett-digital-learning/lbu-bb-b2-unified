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

import blackboard.data.registry.SystemRegistryUtil;
import blackboard.platform.plugin.PlugInException;
import blackboard.platform.plugin.PlugInUtil;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebListener;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import uk.ac.leedsbeckett.bbb2utils.peertopeer.BuildingBlockCoordinator;
import uk.ac.leedsbeckett.bbb2utils.peertopeer.BuildingBlockPeerMessageListener;

/**
 * One of these is instantiated when the servlet container starts the
 * web application. It is initialized exactly once and destroyed
 * exactly once.  A ServletContext is also created exactly once and
 * is passed to the methods of this listener.
 * 
 * The WebListener annotation means that the servlet container finds it with no
 * need to reference it in any configuration files.
 * 
 * @author jon
 */
public class WebAppCore implements ServletContextListener, BuildingBlockPeerMessageListener
{
  public final static String CONTEXT_ATTRIBUTE_WEBAPPCORE = WebAppCore.class.getCanonicalName();
  public final static String CONTEXT_ATTRIBUTE_BOOTLOG    = WebAppCore.class.getCanonicalName() + ".bootlog";
  
  public Logger logger = null;
  File propsfile;
  UnifiedBuildingBlockProperties configproperties;
  final String buildingblockhandle = "bbb2unified";
  final String buildingblockvid    = "LBU";

  public Path logbase=null;
  public Path pluginbase=null;
  public Path configbase=null;

  String serverid;  

  StringBuffer bootlog = new StringBuffer();
  String booterror = null;

  BuildingBlockCoordinator bbcoord;
  
  public static WebAppCore getInstance( ServletRequest request )
  {
    return getInstance( request.getServletContext() );
  }
  
  public static WebAppCore getInstance( ServletContext sc )
  {
    return (WebAppCore)sc.getAttribute( CONTEXT_ATTRIBUTE_WEBAPPCORE );
  }
  
  public static String getBootLog( ServletRequest request )
  {
    StringBuffer bootlog = (StringBuffer)request.getServletContext().getAttribute( CONTEXT_ATTRIBUTE_BOOTLOG );
    if ( bootlog == null ) return "No boot log.";
    return bootlog.toString();
  }
  
  public static String getWebAppCoreBootError( ServletRequest request )
  {
    WebAppCore webappcore = WebAppCore.getInstance( request );
    if ( webappcore == null ) return "WebAppCore has not started.";
    return webappcore.booterror;
  }
  
  public void logException( Exception e )
  {
    logger.error( "Exception ", e );
  }
  
  @Override
  public void contextInitialized(ServletContextEvent sce)
  {
    bootlog.append( "Started boot log.\n" );
    try
    {
      init( sce.getServletContext() );
    }
    catch ( Exception e )
    {
      booterror = e.toString();
      bootlog.append( "\nException thrown. " + e + "\n" );
      StringWriter writer = new StringWriter();
      e.printStackTrace( new PrintWriter( writer ) );
      bootlog.append( writer.getBuffer().toString() );
    }    
  }

  void init( ServletContext servletcontext ) throws IOException, PlugInException, JMSException
  {
    servletcontext.setAttribute( CONTEXT_ATTRIBUTE_WEBAPPCORE, this );
    servletcontext.setAttribute( CONTEXT_ATTRIBUTE_BOOTLOG, bootlog );
    serverid = InetAddress.getLocalHost().getHostName();
    bootlog.append( "Started boot log.\n" );
    
    configbase = Paths.get( PlugInUtil.getConfigDirectory( buildingblockvid, buildingblockhandle ).getPath() );
    logbase    = configbase.resolve( "log" );
    pluginbase = configbase.getParent();      
    initLogging();
    setPropertiesFile();
    configproperties = new UnifiedBuildingBlockProperties( logger );
    loadProperties();
    applyProperties();

    bbcoord = new BuildingBlockCoordinator( buildingblockvid, buildingblockhandle, serverid, this, logger );
    bbcoord.setPingRate( 300 );
    // This is an asynchronous start.  It creates a thread to start the messaging
    // but returns right away. This is because there could be issues connecting to
    // the message broker over the network that could make starting the building block
    // hang for ages.
    bbcoord.start();
  }
  
  @Override
  public void contextDestroyed(ServletContextEvent sce)
  {
    try
    {
      bbcoord.destroy();
    }
    catch ( JMSException ex )
    {
      logger.error( "Problem destroying bb coordinator", ex );
    }
  }


  /**
   * Manually configure logging so that the log files for this application
   * go where we want them and not into general log files for BB.
   * @param logfilefolder 
   */
  public void initLogging(  ) throws IOException
  {
    if ( !Files.exists( logbase ) )
      Files.createDirectory( logbase );
    
    logger = LogManager.getLoggerRepository().getLogger(WebAppCore.class.getName() );
    logger.setLevel( Level.INFO );
    String logfilename = logbase.resolve( serverid + ".log" ).toString();
    RollingFileAppender rfapp = 
        new RollingFileAppender( 
            new PatternLayout( "%d{ISO8601} %-5p: %m%n" ), 
            logfilename, 
            true );
    rfapp.setMaxBackupIndex( 100 );
    rfapp.setMaxFileSize( "2MB" );
    logger.removeAllAppenders();
    logger.addAppender( rfapp );
    logger.info( "==========================================================" );
    logger.info( "Log file " + logfilename + " has been opened." );
    logger.info( "==========================================================" );
  }

  public String getBootLog()
  {
    return bootlog.toString();
  }
  
  public String getLogBase()
  {
    return logbase.toString();
  }
  
  
  
  /**
   * Load the variable properties file and start logging.
   * @return Success
   */
  public boolean setPropertiesFile()
  {
    try
    {      
      propsfile = configbase.resolve( buildingblockhandle + ".properties" ).toFile();
      logger.info("Config properties file is here: " + propsfile );
      if ( !propsfile.exists() )
      {
        logger.info( "Doesn't exist so creating it now." );
        propsfile.createNewFile();
      }
    }
    catch ( Throwable th )
    {
      return false;            
    }
    return true;
  }

  public boolean loadProperties()
  {
    logger.info( "loadProperties()" );
    try ( FileReader reader = new FileReader( propsfile ) )
    {
      configproperties.load(reader);
      for ( var key : configproperties.keySet() )
        logger.debug( "Property " + key + " = " + configproperties.getProperty( key.toString() ) );
      return true;
    }
    catch (Exception ex)
    {
      logger.error( "Unable to load properties from file.", ex );
      return false;
    }    
  }
  
  public boolean applyProperties()
  {
    logger.setLevel( configproperties.getLogLevel() );
//      if ( bbcoord != null )
//        bbcoord.setPingRate( configproperties.getPingRate() );
    return true;
  }
  
  public void saveProperties()
  {
    try ( FileWriter writer = new FileWriter( propsfile ) )
    {
      configproperties.store(writer, serverid);
      bbcoord.sendTextMessageToAll( "reconfigure" );
      logger.info( "Saved settings and told all servers." );
    }
    catch (Exception ex)
    {
      logger.error( "Unable to save properties to file.", ex );
    }
  }

  
  
  @Override
  public void consumeMessage(Message msg)
  {
    if ( !(msg instanceof TextMessage ) )
      return;
    TextMessage tm = (TextMessage)msg;
    try
    {
      String m = tm.getText();
      if ( "reconfigure".equals( m ) )
      {
        loadProperties();
        applyProperties();
      }
    }
    catch (JMSException ex)
    {
      logger.error( "Unable to message other servers to reload properties.", ex );
    }
  }
  
  
  MimeMessage getBbEmail()
  {
    Properties bbprops = blackboard.platform.config.ConfigurationServiceFactory.getInstance().getBbProperties();
    String smtpHost = bbprops.getProperty("bbconfig.smtpserver.hostname");
    String dBsmtpHost = SystemRegistryUtil.getString("smtpserver_hostname", smtpHost);
    if (!StringUtils.isEmpty( dBsmtpHost ) && !"0.0.0.0".equals( dBsmtpHost ) )
      smtpHost = dBsmtpHost;
    if ( logger != null ) logger.debug( "Using " + smtpHost );
    
    Properties mailprops = new Properties();
    mailprops.setProperty("mail.smtp.host", smtpHost);
    Session mailSession = Session.getDefaultInstance(mailprops);
    
    return new MimeMessage(mailSession);
  }

  public void sendPlainEmail(
          String subject, 
          InternetAddress from, 
          InternetAddress[] reply, 
          InternetAddress[] recipients, 
          InternetAddress[] courtesycopies, 
          String message) throws MessagingException
  {
    MimeMessage email = getBbEmail();
    MimeMultipart multipart = new MimeMultipart();
    BodyPart messageBodyPart = new MimeBodyPart();

    email.setSubject(subject);
    if ( reply != null && reply.length > 0 )
      email.setReplyTo( reply );
    email.setFrom( from );
    messageBodyPart.setContent(message, "text/plain");
    multipart.addBodyPart(messageBodyPart);
    email.setRecipients( javax.mail.Message.RecipientType.TO, recipients );
    if ( courtesycopies != null && courtesycopies.length > 0 )
      email.setRecipients( javax.mail.Message.RecipientType.CC, courtesycopies );
    email.setContent(multipart);
    Transport.send(email);
  }
  
}
