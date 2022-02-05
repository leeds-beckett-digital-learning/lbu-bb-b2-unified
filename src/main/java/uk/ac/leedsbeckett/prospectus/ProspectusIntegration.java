package uk.ac.leedsbeckett.prospectus;

//import uk.ac.leedsbeckett.banner.Concentration;
import com.fasterxml.jackson.core.JsonProcessingException;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import org.apache.log4j.Logger;
import uk.ac.leedsbeckett.bbb2utils.json.JsonConvertor;

/**
 * Some of the code from the portal_channel JSP is refactored here, for clarity.
 * This class contains code related to the integration between this Building
 * Block and the Prospectus API. Created by vergil01 on 08/08/2018.
 */
public class ProspectusIntegration
{

  String url;
  String token;

  /**
   * The Roles we are interested in displaying
   */
  public static final List<String> COURSE_ROLES = Arrays.asList( "Course Administrator", "Course Director" );

  public Logger logger;

  public ProspectusIntegration( String url, String token, Logger logger )
  {
    this.url = url;
    this.token = token;
    this.logger = logger;
  }

  /**
   * This method calls getCourseContactsJSON() to retrieve the Course Contacts,
   * then filters by role to retrieve only the ones specified in the
   * COURSE_ROLES list.
   *
   * @param jobTitle The Course data fed to Blackboard via an integration
   * report.
   * @return Returns a Map of Course Contacts by type (role).
   * @throws com.fasterxml.jackson.core.JsonProcessingException
   */
  public List<CourseContact> getCourseContactsMapByType( String jobTitle ) throws JsonProcessingException
  {
    if ( jobTitle == null || !jobTitle.contains( "|" ) )
    {
      return null;
    }
    String str = getCourseContacts( jobTitle );
    JsonConvertor<CourseContact[]> jc = new JsonConvertor<>( CourseContact[].class );
    CourseContact[] contacts = jc.read( str );
    Arrays.sort( contacts, new CourseContactComparator() );
    ArrayList<CourseContact> list = new ArrayList<>();
    for ( CourseContact cc : contacts )
    {
      if ( list.isEmpty() || list.get( list.size() - 1 ).isDifferentPerson( cc ) )
      {
        list.add( cc );
      }
    }
    return list;
  }

  /**
   * This method calls the Prospectus API via a HTTPS connection to retrieve
   * Course Contact data.
   *
   * @param jobTitle The Course data fed to Blackboard via an integration
   * report.
   * @return A JsonArray containing all Contacts associated with a Course.
   */
  public String getCourseContacts( String jobTitle )
  {
    StringBuilder content = new StringBuilder();
    if ( jobTitle != null && !jobTitle.isEmpty() && jobTitle.indexOf( "|" ) > 0 )
    {
      //Get the Prospectus URL
      URL courseContactsUrl = getProspectusUrlWithStaticToken( jobTitle );

      //Build the request for the Prospectus API
      try
      {
        HttpsURLConnection connection = (HttpsURLConnection) courseContactsUrl.openConnection();
        connection.setRequestMethod( "GET" );
        connection.setRequestProperty( "Content-Type", "application/json" );
        connection.setConnectTimeout( 10000 ); //Set to a generous 10sec, it takes a while to connect.
        connection.setReadTimeout( 10000 ); //Same here
        try ( BufferedReader reader = new BufferedReader( new InputStreamReader( connection.getInputStream() ) ) )
        {
          String line;
          while ( ( line = reader.readLine() ) != null )
          {
            content.append( line );
          }
        }

      }
      catch ( IOException e )
      {
        logger.error( "Problem fetching contact list from server.", e );
      }
    }
    return content.toString();
  }

  private URL getProspectusUrlWithStaticToken( String jobTitle )
  {
    return getProspectusUrl( jobTitle, this::getStaticToken );
  }

  private URL getProspectusUrl( String jobTitle, Supplier<String> tokenGetter )
  {
    URL url = null;
    try
    {
      url = new URL( getBaseUrl()
              .append( getCourse( jobTitle ) )
              .append( tokenGetter.get() )
              .toString() );
    }
    catch ( MalformedURLException e )
    {
      logger.error( "Problem forming url to prospectus server.", e );
    }
    return url;
  }

  private StringBuffer getBaseUrl()
  {
    StringBuffer requestPage = new StringBuffer();
    requestPage.append( url );
    return requestPage;
  }

  protected String getCourse( String jobTitle )
  {
    String course = null;
    if ( jobTitle != null && jobTitle.contains( "|" ) )
    {
      course = jobTitle
              .substring( 0, jobTitle.indexOf( "|" ) )
              .replace( "-", "/" ); //This will be in the format "PSYCO/A002/FULL"
    }
    return replaceConcentration( course );
  }

  private String replaceConcentration( String course )
  {
    String result = "";
    if ( course != null && course.contains( "/" ) )
    {
      String concentration = course.substring( course.lastIndexOf( "/" ) + 1 );
      result = course.replace( concentration, Concentration.valueOf( concentration ).getCode() );
    }
    return result;
  }

  private String getStaticToken()
  {
    return "?token=" + token;
  }
}
