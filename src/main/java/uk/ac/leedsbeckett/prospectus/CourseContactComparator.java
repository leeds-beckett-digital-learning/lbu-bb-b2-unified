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

package uk.ac.leedsbeckett.prospectus;

import java.util.Comparator;

/**
 *
 * @author maber01
 */
public class CourseContactComparator implements Comparator<CourseContact>
{
  @Override
  public int compare( CourseContact o1, CourseContact o2 )
  {
    int result;
    result = o1.Role.compareTo( o2.Role );
    if ( result != 0 ) return result;
    result = o1.Name.compareTo( o2.Name );
    if ( result != 0 ) return result;
    result = o1.Email.compareTo( o2.Email );
    if ( result != 0 ) return result;
    result = o1.Primary.compareTo( o2.Primary );
    if ( result != 0 ) return -result;
    return 0;
  }
}
