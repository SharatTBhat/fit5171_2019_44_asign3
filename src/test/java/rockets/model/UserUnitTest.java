package rockets.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class UserUnitTest {
    private User target;

    @BeforeEach
    public void setUp() {
        target = new User();
    }


    @DisplayName("should throw exception when pass a empty email address to setEmail function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetEmailToEmpty(String email) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setEmail(email));
        assertEquals("email cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setEmail function")
    @Test
    public void shouldThrowExceptionWhenSetEmailToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setEmail(null));
        assertEquals("email cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a empty password to setPassword function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetPasswordToEmpty(String password) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setPassword(password));
        assertEquals("password cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exceptions when pass a null password to setPassword function")
    @Test
    public void shouldThrowExceptionWhenSetPasswordToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setPassword(null));
        assertEquals("password cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should return true when two users have the same email")
    @Test
    public void shouldReturnTrueWhenUsersHaveSameEmail() {
        String email = "abc@example.com";
        target.setEmail(email);
        User anotherUser = new User();
        anotherUser.setEmail(email);
        assertTrue(target.equals(anotherUser));
    }


    @DisplayName("should return false when two users have different emails")
    @Test
    public void shouldReturnFalseWhenUsersHaveDifferentEmails() {
        target.setEmail("abc@example.com");
        User anotherUser = new User();
        anotherUser.setEmail("def@example.com");
        assertFalse(target.equals(anotherUser));
    }

    @DisplayName("should throw exception when pass a special character to setFirstName function")
    @ParameterizedTest
    @ValueSource(strings = {"#re", " &&", "9"})
    public void shouldThrowExceptionWhenPassSpecialCharacterToFirstName(String firstName) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setFirstName(firstName));
        assertEquals("Please using alphabetic characters", exception.getMessage());
    }

    @DisplayName("should throw exception when pass invalid format of address to setEmail function")
    @ParameterizedTest
    @ValueSource(strings = {"#re", " &&.com", "9.com"})
    public void shouldThrowExceptionWhenPassInvalidStringToEmail(String email) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setEmail(email));
        assertEquals("Invalid Email format", exception.getMessage());
    }

    @DisplayName("should throw exception when pass empty name to setFirstName function")
    @ParameterizedTest
    @ValueSource(strings = {"   ", " ", ""})
    public void shouldThrowExceptionWhenPassEmptyNameToFirstName(String firstName) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setFirstName(firstName));
        assertEquals("firstName cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null value to setFirstName function")
    @Test
    public void shouldThrowExceptionWhenPassNullValueToFirstName() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setFirstName(null));
        assertEquals("firstName cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a special character to setLastName function")
    @ParameterizedTest
    @ValueSource(strings = {"#re", " &&", "9"})
    public void shouldThrowExceptionWhenPassSpecialCharacterToLastName(String lastName) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setLastName(lastName));
        assertEquals("Please using alphabetic characters", exception.getMessage());
    }

    @DisplayName("should throw exception when pass empty name to setLastName function")
    @ParameterizedTest
    @ValueSource(strings = {"   ", " ", ""})
    public void shouldThrowExceptionWhenPassEmptyNameToLastName(String lastName) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->target.setLastName(lastName));
        assertEquals("firstName cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null value to setLastName function")
    @Test
    public void shouldThrowExceptionWhenPassNullValueToLastName() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setLastName(null));
        assertEquals("firstName cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should return true when password matches")
    @ParameterizedTest
    @ValueSource(strings = {"qazwsxedc12"})
    public void shouldReturnTrueWhenPasswordMatches(String password) {
        target.setPassword("qazwsxedc12");
        assertTrue(target.isPasswordMatch(password));
    }


    @DisplayName("should throw exception when password less than 8 character or do not at least have a character and a number")
    @ParameterizedTest
    @ValueSource(strings = {"a","sds23","aaaaaaaaa","12345677"})
    public void shouldThrowExceptionWhenPasswordLessThanEightCharacter(String password) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setPassword(password));
        assertEquals("Password should more than 8 characters and at least have one character and one number", exception.getMessage());
    }

    @DisplayName("should return false when user want to set an existing email")
    @Test
    public void shouldReturnFalseWhenUserWantToSetAnExistingEmail() {
        User user=new User();
        user.setEmail("aabb@cc.com");
        assertFalse(target.checkUnique("aabb@cc.com",user));
    }

    @DisplayName("should return true when user want to set an existing email")
    @Test
    public void shouldReturnTrueWhenUserWantToSetAnExistingEmail() {
        User user=new User();
        user.setEmail("aab@cc.com");
        assertTrue(target.checkUnique("aabb@cc.com",user));
    }

}
