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

import java.util.List;
import uk.ac.leedsbeckett.prospectus.CourseContact;
import uk.ac.leedsbeckett.prospectus.ProspectusIntegration;

/**
 *
 * @author maber01
 */
public class ContactsOutcomes extends BaseOutcomes
{
  List<CourseContact> courseContacts = null;
  String rawjson = null;

  public ContactsOutcomes()
  {
    super();
    if ( errormessage != null ) return;

    try
    {
        // At LBU a student's job title stores coded course and (PT/FT)
        String jobTitle = user.getJobTitle();

        webappcore.logger.info( "Constructing ContactsOutcomes" );
        //Prospectus Integration
        ProspectusIntegration pi = new ProspectusIntegration( 
                webappcore.configproperties.getProspectusUrl(),
                webappcore.configproperties.getProspectusSecurityToken(),
                webappcore.logger );
        courseContacts = pi.getCourseContactsMapByType(jobTitle);
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
  
  public boolean hasCourseContacts()
  {
    return courseContacts != null && !courseContacts.isEmpty();
  }
  
  public List<CourseContact> getCourseContacts()
  {
    return courseContacts;
  }

  public String getCourseAdminTitle()
  {
    return "Course Administrator";
  }
  public String getCourseDirectorTitle()
  {
    return "Course Director";
  }
  public String getRawJSON()
  {
    return rawjson;
  }
}
