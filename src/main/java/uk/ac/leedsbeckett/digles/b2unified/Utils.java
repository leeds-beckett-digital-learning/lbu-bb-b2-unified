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
import blackboard.data.course.Course;
import blackboard.data.course.CourseMembership;
import blackboard.data.user.User;
import blackboard.persist.KeyNotFoundException;
import blackboard.persist.PersistenceException;
import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.course.CourseMembershipDbLoader;
import blackboard.persist.course.CourseMembershipDbPersister;

/**
 *
 * @author maber01
 */
public class Utils
{
  public static boolean isMember( Course course, User user )
          throws PersistenceException, ValidationException
  {
    CourseMembership membership;
    try
    {
      membership = CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId( course.getId(), user.getId() );
    }
    catch ( KeyNotFoundException ex)
    {
      return false;
    }
    return membership != null;
  }  
  
  public static Course getCourse( String courseid )
          throws PersistenceException
  {
    try
    {
      return CourseDbLoader.Default.getInstance().loadByCourseId( courseid );
    }
    catch ( KeyNotFoundException knfe )
    {
      return null;
    }  
  }
  
  public static void enrollUser( Course course, User user, CourseMembership.Role role )
          throws PersistenceException, ValidationException
  {
    CourseMembershipDbPersister cmPersister = CourseMembershipDbPersister.Default.getInstance();
    CourseMembership membership = new CourseMembership();
    membership.setCourseId( course.getId() );
    membership.setUserId( user.getId() );
    membership.setRole( role );
    cmPersister.persist( membership );
  }
  
}
