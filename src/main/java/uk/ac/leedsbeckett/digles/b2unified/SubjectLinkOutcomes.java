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

/**
 *
 * @author maber01
 */
public class SubjectLinkOutcomes extends BaseOutcomes
{
  String school = null;
  String schoollink = null;
  
  private static final String SCHOOLBASE = "http://libguides.leedsbeckett.ac.uk/schools/";
  private static final String[][] TABLE = 
  {
    { "Carnegie School of Education",                            "education_and_childhood"           },
    { "Carnegie School of Sport",                                "sport"                             },
    { "Leeds Business School",                                   "leeds_business_school"             },
    { "Leeds Law School" ,                                       "leeds_law_school"                  },
    { "Leeds School of Arts" ,                                   "leeds_school_of_arts"              },
    { "Leeds School of Social Sciences" ,                        "social_sciences"                   },
    { "School of Built Environment, Engineering and Computing" , "built_environment_and_engineering" },
    { "School of Clinical and Applied Sciences" ,                "clinical_and_applied_sciences"     },
    { "Cultural Studies and Humanities" ,                        "cultural_studies_and_humanities"   },
    { "School of Events, Tourism and Hospitality Management" ,   "events_tourism_hospitality"        },
    { "School of Health and Community Studies" ,                 "health_and_community_studies"      }
  };
          
  public SubjectLinkOutcomes()
  {
    super();
    if ( errormessage != null ) return;

    try
    {
      school = user.getDepartment();
      for (String[] TABLE1 : TABLE) {
          if (TABLE1[0].equals(school)) {
              schoollink = TABLE1[1]; 
          }
      }
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

  public boolean hasSchool()
  {
      return schoollink != null;
  }
  
  public String getSchoolName()
  {
    return school;
  }
  
  public String getSchoolLink()
  {
    return SCHOOLBASE + schoollink;
  }
}
