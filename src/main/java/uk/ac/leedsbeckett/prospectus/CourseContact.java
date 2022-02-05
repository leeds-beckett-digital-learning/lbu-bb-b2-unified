package uk.ac.leedsbeckett.prospectus;

import java.util.Comparator;

/**
 * Created by vergil01 on 09/08/2018.
 */
public class CourseContact
{
    public String Name;
    public String Email;
    public String Role;
    public String Primary;

    boolean isPrimary()
    {
        return "Y".equals(Primary );
    }
    
    @Override
    public String toString() {
        return "CourseContact{" +
                "name='" + Name + '\'' +
                ", email='" + Email + '\'' +
                ", role='" + Role + '\'' +
                ", primary=" + Primary +
                '}';
    }
    
    public boolean isDifferentPerson( CourseContact other )
    {
      return !this.Name.equals( other.Name )   || 
             !this.Email.equals( other.Email ) || 
             !this.Role.equals( other.Role );
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getRole() {
        return Role;
    }

    public String getPrimary() {
        return Primary;
    }
    
    
}
