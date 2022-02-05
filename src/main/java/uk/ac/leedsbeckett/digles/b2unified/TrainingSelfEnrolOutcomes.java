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
import blackboard.persist.SearchOperator;
import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.course.CourseSearch;
import blackboard.platform.plugin.PlugInUtil;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author maber01
 */
public class TrainingSelfEnrolOutcomes extends BaseOutcomes
{
  ArrayList<Course> courselist;
          
  public TrainingSelfEnrolOutcomes()
  {
    try
    {
      if ( !staff )
      {
        errormessage = "This tool is only available to users whose primary role is staff.";
        return;
      }
      
      CourseSearch cs = CourseSearch.getViewCatalogSearch(
            CourseSearch.SearchKey.CourseId,
            SearchOperator.StartsWith,
            webappcore.configproperties.getTrainingCoursePrefix(),
            SearchOperator.LessThan,
            null,
            null,
            Course.ServiceLevel.FULL
        );
        ArrayList<Course> unfilteredcourselist;
        unfilteredcourselist = (ArrayList<Course>) CourseDbLoader.Default.getInstance().loadByCourseSearch(cs);
        courselist = new ArrayList<>();
        for ( Course c : unfilteredcourselist )
          if ( c.getCourseId().startsWith( webappcore.configproperties.getTrainingCoursePrefix() ) )
            courselist.add( c );
        
        courselist.sort( new Comparator<Course>()
                                  {
                                    @Override
                                    public int compare( Course o1, Course o2 )
                                    {
                                      return o1.getTitle().compareTo( o2.getTitle() );
                                    }
                                  }
        );
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
    return PlugInUtil.getUri("LBU", "bbb2unified", "servlets/enrolontraining/");
  }
  
  public String getStylesheetURL()
  {
    return PlugInUtil.getUri("LBU", "bbb2unified", "trainingselfenrol/style.css");
  }
  
  public String getStyleColour()
  {
    return webappcore.configproperties.getTrainingColourScheme();
  }
  
  public String getTitle()
  {
    return webappcore.configproperties.getTrainingTitle();
  }
  
  public String getButtonLabel()
  {
    return webappcore.configproperties.getTrainingButtonLabel();
  }
  public String getInfoText()
  {
    return webappcore.configproperties.getTrainingInfoHtml().getFormattedText();
  }

  public String getHelpText()
  {
    return webappcore.configproperties.getTrainingHelpHtml().getFormattedText();
  }
  
  public boolean noCoursesFound()
  {
    return courselist == null || courselist.size() == 0;
  }
  
  public int getCourseCount()
  {
    return courselist.size();
  }
  
  public String getCourseTitle( int n )
  {
    return courselist.get( n ).getTitle();
  }

  public String getCourseID( int n )
  {
    return courselist.get( n ).getCourseId();
  }
  
  public String getCourseDisabledOption( int n )
  {
    return ""; // disabled=\"true\"";
  }
}
