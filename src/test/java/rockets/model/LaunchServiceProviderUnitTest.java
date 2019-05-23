package rockets.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LaunchServiceProviderUnitTest {
    //testing
    private LaunchServiceProvider target;
    @BeforeEach
    public void setUp() {
        target = new LaunchServiceProvider("NASA",1987,"America");
    }

    @DisplayName("should throw exception when pass Empty to name")
    @Test
    public void shouldThrowExceptionWhenSetEmptyToName() {
        Exception exception=assertThrows(IllegalArgumentException.class, ()-> new LaunchServiceProvider("",1993,"Australia") );
        assertEquals("Name cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass negtive number to constructor")
    @Test
    public void shouldThrowExceptionWhenSetNegativeFoundYearToLaunchServiceProvider() {
        Exception exception=assertThrows(IllegalArgumentException.class, ()-> new LaunchServiceProvider("s",2023,"Australia") );
        assertEquals("Invalid years", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to constructor")
    @Test
    public void shouldThrowExceptionWhenSetNullToName() {
        Exception exception=assertThrows(NullPointerException.class, ()-> new LaunchServiceProvider(null,1993,"Australia") );
        assertEquals("Name cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass empty to country")
    @Test
    public void shouldThrowExceptionWhenSetEmptyToCountry() {
        Exception exception=assertThrows(IllegalArgumentException.class, ()-> new LaunchServiceProvider("steve",1,"") );
        assertEquals("Country cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to country")
    @Test
    public void shouldThrowExceptionWhenSetNullToCountry() {
        Exception exception=assertThrows(NullPointerException.class, ()-> new LaunchServiceProvider("steve",1,null ));
        assertEquals("Country cannot be null or empty", exception.getMessage());
    }


    @DisplayName("should throw exception when pass null to headquarters")
    @Test
    public void shouldThrowExceptionWhenSetNullToHeadquarters() {
        Exception exception=assertThrows(NullPointerException.class, ()-> target.setHeadquarters(null));
        assertEquals("Headquarters cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass empty to headquarters")
    @Test
    public void shouldThrowExceptionWhenSetEmptyToHeadquarters() {
        Exception exception=assertThrows(IllegalArgumentException.class, ()-> target.setHeadquarters(""));
        assertEquals("Headquarters cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when give null to rocket set")
    @Test
    public void shouldThrowExceptionWhenSetNullToRocketSet() {
        Exception exception=assertThrows(IllegalArgumentException.class, ()-> target.setRockets(null));
        assertEquals("Rocket sets cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when give null to rocket set")
    @Test
    public void shouldThrowExceptionWhenGiveEmptySetToRocketSet() {
        Set<Rocket> rocketSet=new HashSet<Rocket>();
        Exception exception=assertThrows(IllegalArgumentException.class, ()-> target.setRockets(rocketSet));
        assertEquals("Rocket sets cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should return true when two users have the same name, founded year and country")
    @Test
    public void shouldReturnTrueWhenUsersHaveSameNameFoundedYearAndCountry() {
        LaunchServiceProvider anotherUser = new LaunchServiceProvider("NASA",1987,"America");
        assertTrue(target.equals(anotherUser));
    }

    @DisplayName("should throw exception when given a special character to name")
    @ParameterizedTest
    @ValueSource(strings = {"&al", "##", "9"})
    public void shouldThrowExceptionWhenGivenSpecialCharacterToName(String name) {
        Exception exception=assertThrows(IllegalArgumentException.class, ()-> new LaunchServiceProvider(name,1999,"Australia"));
        assertEquals("Please using alphabetic characters for name", exception.getMessage());
    }

    @DisplayName("should throw exception when given a special character to country")
    @ParameterizedTest
    @ValueSource(strings = {"&al", "##", "9","nasa9"})
    public void shouldThrowExceptionWhenGivenSpecialCharacterToCountry(String country) {
        Exception exception=assertThrows(IllegalArgumentException.class, ()-> new LaunchServiceProvider("Alex",1999,country));
        assertEquals("Please using alphabetic characters for country", exception.getMessage());
    }

    @DisplayName("should throw exception when given a special character to headquaters")
    @ParameterizedTest
    @ValueSource(strings = {"&al", "##", "9"})
    public void shouldThrowExceptionWhenGivenSpecialCharacterToHeadquarters(String headquarters) {
        Exception exception=assertThrows(IllegalArgumentException.class, ()-> target.setHeadquarters(headquarters));
        assertEquals("Please using alphabetic characters for headquarters", exception.getMessage());
    }

    @DisplayName("should return false when two users have the different name, founded year and country")
    @Test
    public void shouldReturnTrueWhenUsersHaveDifferentNameFoundedYearAndCountry() {
        LaunchServiceProvider anotherUser = new LaunchServiceProvider("John",1935,"Australia");
        assertFalse(target.equals(anotherUser));
    }
}