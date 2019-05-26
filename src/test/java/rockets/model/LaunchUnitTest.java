package rockets.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LaunchUnitTest {
    private Launch target;
    //
    @BeforeEach
    public void setUp() {
        target = new Launch();
    }

    //launch site
    @DisplayName("should throw exception when pass non alphabetic characters to setLaunchSite function")
    @ParameterizedTest
    @ValueSource(strings = {"site##@", " @*#", "sitesa9#"})
    public void shouldThrowExceptionWhenPassSpecialCharacterToLaunchSite(String launchSite) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setLaunchSite(launchSite));
        assertEquals("Please using alphabetic characters", exception.getMessage());
    }



    @DisplayName("should throw exception when pass empty name to setLaunchSite function")
    @ParameterizedTest
    @ValueSource(strings = {"   ", " ", ""})
    public void shouldThrowExceptionWhenPassEmptyNameToLaunchSite(String launchSite) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setLaunchSite(launchSite));
        assertEquals("Launch site cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null value to setLaunchSite  function")
    @Test
    public void shouldThrowExceptionWhenPassNullValueToLaunchSite() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setLaunchSite(null));
        assertEquals("Launch site cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass non alphabetic characters to Set Orbit function")
    @ParameterizedTest
    @ValueSource(strings = {"orbit##@", " @*#", "orbitsa9#"})
    public void shouldThrowExceptionWhenPassSpecialCharacterToSetOrbit(String orbit) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setOrbit(orbit));
        assertEquals("Please using alphabetic characters", exception.getMessage());
    }



    @DisplayName("should throw exception when pass empty name to Set Orbit function")
    @ParameterizedTest
    @ValueSource(strings = {"   ", " ", ""})
    public void shouldThrowExceptionWhenPassEmptyNameToSetOrbit(String orbit) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setOrbit(orbit));
        assertEquals("Orbit cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null value to Set Orbit function")
    @Test
    public void shouldThrowExceptionWhenPassNullValueToSetOrbit() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setOrbit(null));
        assertEquals("Orbit cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass non alphabetic characters to Set launch function")
    @ParameterizedTest
    @ValueSource(strings = {"orbit##@", " @*#","aaa9"})
    public void shouldThrowExceptionWhenPassSpecialCharacterToSetFunction(String function) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setFunction(function));
        assertEquals("Please using alphabetic characters", exception.getMessage());
    }

    @DisplayName("should throw exception when pass empty name to Set launch function")
    @ParameterizedTest
    @ValueSource(strings = {"   ", " ", ""})
    public void shouldThrowExceptionWhenPassEmptyNameToSetFunction(String function) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setFunction(function));
        assertEquals("Launch function cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null value to Set Launch function")
    @Test
    public void shouldThrowExceptionWhenPassNullValueToSetFunction() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setFunction(null));
        assertEquals("Launch function cannot be null or empty", exception.getMessage());
    }

    //Price
    @DisplayName("should throw exception when pass negative or 0 value to set price")
    @ParameterizedTest
    @ValueSource(strings = {"-1", "0"})
    public void shouldThrowIllegalArgumentExceptionWhenGivenNegativeInput(BigDecimal price) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setPrice(price));
        assertEquals("Price should larger than 0", exception.getMessage());
    }

}
