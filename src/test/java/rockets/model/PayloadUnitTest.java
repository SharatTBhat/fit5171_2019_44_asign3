package rockets.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PayloadUnitTest {
    private Payload target;

    @BeforeEach
    public void setUp() {
        target = new Payload();
    }

//    @DisplayName("should throw exception when pass null value to Set capacity function")
//    @Test
//    public void shouldThrowExceptionWhenPassNullValueToSetCapacity() {
//        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setCapacity(null));
//        assertEquals("Capacity cannot be null or empty", exception.getMessage());
//    }

    @DisplayName("should throw exception when pass negative value to Set capacity function")
    @ParameterizedTest
    @ValueSource(longs = {-1,-2,-3})
    public void shouldThrowExceptionWhenPassEmptyNameToSetCapacity(Long capacity) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setCapacity(capacity));
        assertEquals("Capacity cannot be negative value", exception.getMessage());
    }

//    @DisplayName("should throw exception when given negative number or non number characters to Set capacity function")
//    @ParameterizedTest
//    @ValueSource(strings = {"abc", " @#", "-123"})
//    public void shouldThrowExceptionWhenPassSpecialCharacterToSetCapacity(String capacity) {
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setCapacity(capacity));
//        assertEquals("Please using numbers that above zero and within 10 digits", exception.getMessage());
//    }


    @DisplayName("should throw exception when pass null value to Set capacity function")
    @Test
    public void shouldThrowExceptionWhenPassNullValueToSetPayloadType() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setPayloadType(null));
        assertEquals("PayloadType cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass empty name to Set payLoadType function")
    @ParameterizedTest
    @ValueSource(strings = {"   ", " ", ""})
    public void shouldThrowExceptionWhenPassEmptyNameToSetPayloadType(String payLoadType) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setPayloadType(payLoadType));
        assertEquals("PayloadType cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when given negative number or non number characters to Set payLoadType function")
    @ParameterizedTest
    @ValueSource(strings = {"abc##", " @#", "123"})
    public void shouldThrowExceptionWhenPassSpecialCharacterToSetPayloadType(String payLoadType) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setPayloadType(payLoadType));
        assertEquals("Please using alphabetic characters", exception.getMessage());
    }

}