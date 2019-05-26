package rockets.model;

import org.neo4j.ogm.annotation.NodeEntity;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.Validate.notBlank;

@NodeEntity
public class User extends Entity {
    private String firstName;

    private String lastName;

    private String email;

    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        notBlank(firstName, "firstName cannot be null or empty");
        String regEx ="^[a-zA-Z\\s]+";
        Pattern p = Pattern.compile(regEx);
        Matcher m=p.matcher(firstName);
        if (!m.matches())
        {
            throw new IllegalArgumentException("Please using alphabetic characters");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        notBlank(lastName, "firstName cannot be null or empty");
        String regEx ="^[a-zA-Z\\s]+";
        Pattern p = Pattern.compile(regEx);
        Matcher m=p.matcher(lastName);
        if (!m.matches())
        {
            throw new IllegalArgumentException("Please using alphabetic characters");
        }
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        notBlank(email, "email cannot be null or empty");
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        Matcher matcher = pattern.matcher(email);
        if(matcher.matches()){
            this.email = email;
        }
        else{
            throw new IllegalArgumentException("Invalid Email format");
        }

    }

    public String getPassword() {
        return password;
    }

    /*public void setPassword(String password) {
        notBlank(password, "password cannot be null or empty");
        Pattern numberPattern=Pattern.compile("[0-9]+");
        Pattern characterPattern=Pattern.compile("[a-zA-Z]+");
        Matcher numberMatcher=numberPattern.matcher(password);
        Matcher characterMather=characterPattern.matcher(password);
        this.password = password;
    }*/

    public void setPassword(String password) {
        notBlank(password, "password cannot be null or empty");
        Pattern numberPattern=Pattern.compile("[0-9]+");
        Pattern characterPattern=Pattern.compile("[a-zA-Z]+");
        Matcher numberMatcher=numberPattern.matcher(password);
        Matcher characterMather=characterPattern.matcher(password);
        if(password.trim().length()<8|!numberMatcher.find()|!characterMather.find())
        {
            throw new IllegalArgumentException("Password should more than 8 characters and at least have one character and one number");
        }
        this.password = password;
    }

    // match the given password against user's password and return the result
    public boolean isPasswordMatch(String password) {
        return this.password.equals(password.trim());
    }

   public boolean checkUnique(String email,User user) {
        if(user.getEmail().equals(email))
        {
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
