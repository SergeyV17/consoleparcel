package ru.liga.parcelmanager.validation;

import org.junit.jupiter.api.Test;
import ru.liga.parcelmanager.service.NumberOfTrucksValidationService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static ru.liga.parcelmanager.model.enums.LoadingMode.LOADING_TO_CAPACITY;
import static ru.liga.parcelmanager.model.enums.LoadingMode.ONE_BY_ONE;
import static ru.liga.parcelmanager.model.enums.LoadingMode.UNIFORM;

class NumberOfTrucksValidatorTest {

    @Test
    public void testLoadingToCapacity_InsufficientTrucks_ThrowsException() {
        NumberOfTrucksValidationService validator = new NumberOfTrucksValidationService();
        int numberOfTrucks = 1;
        List<String> parcels = Arrays.asList("111111", "111111", "111111", "111111", "111111", "111111", "111111", "111111");
        assertThatCode(() -> validator.validate(numberOfTrucks, parcels, LOADING_TO_CAPACITY))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Number of trucks must be greater");
    }

    @Test
    public void testLoadingToCapacity_SufficientTrucks_NoException() {
        NumberOfTrucksValidationService validator = new NumberOfTrucksValidationService();
        int numberOfTrucks = 2;
        List<String> parcels = Arrays.asList("1", "2", "3");
        assertThatCode(() -> validator.validate(numberOfTrucks, parcels, LOADING_TO_CAPACITY))
                .doesNotThrowAnyException();
    }

    @Test
    public void testOneByOne_IncorrectNumberOfTrucks_ThrowsException() {
        NumberOfTrucksValidationService validator = new NumberOfTrucksValidationService();
        int numberOfTrucks = 2;
        List<String> parcels = Arrays.asList("1", "2", "3");
        assertThatCode(() -> validator.validate(numberOfTrucks, parcels, ONE_BY_ONE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Number of trucks must be equal to the number of parcels in OneByOne mode");
    }

    @Test
    public void testOneByOne_CorrectNumberOfTrucks_NoException() {
        NumberOfTrucksValidationService validator = new NumberOfTrucksValidationService();
        int numberOfTrucks = 3;
        List<String> parcels = Arrays.asList("1", "2", "3");
        assertThatCode(() -> validator.validate(numberOfTrucks, parcels, ONE_BY_ONE))
                .doesNotThrowAnyException();
    }

    @Test
    public void testUniform_InsufficientTrucks_ThrowsException() {
        NumberOfTrucksValidationService validator = new NumberOfTrucksValidationService();
        int numberOfTrucks = 1;
        List<String> parcels = Arrays.asList("111111", "111111", "111111", "111111", "111111", "111111", "111111");
        assertThatCode(() -> validator.validate(numberOfTrucks, parcels, UNIFORM))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("Number of trucks must be greater than or equal to");
    }

    @Test
    public void testUniform_SufficientTrucks_NoException() {
        NumberOfTrucksValidationService validator = new NumberOfTrucksValidationService();
        int numberOfTrucks = 2;
        List<String> parcels = Arrays.asList("1", "2", "3");
        assertThatCode(() -> validator.validate(numberOfTrucks, parcels, UNIFORM))
                .doesNotThrowAnyException();
    }
}