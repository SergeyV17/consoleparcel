package ru.liga.parcelmanager.service;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.Fail.fail;

public class FileValidatorTests {

    @Test
    void validate_EmptyFileLines_ThrowsIllegalArgumentException() {
        var validator = new FileValidationService();
        List<String> fileLines = new ArrayList<>();
        assertThatCode(() -> validator.validate(fileLines)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void validate_ValidFileLines_NoErrors() {
        var validator = new FileValidationService();
        List<String> fileLines = Arrays.asList("111", "222", "333");
        validator.validate(fileLines);
    }

    @Test
    void validate_InvalidFileLines_ThrowsIllegalArgumentException() {
        var validator = new FileValidationService();
        List<String> fileLines = Arrays.asList("123", "abc", "456");
        assertThatCode(() -> validator.validate(fileLines)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void validate_MultipleErrors_ThrowsIllegalArgumentExceptionWithMultipleErrors() {
        var validator = new FileValidationService();
        List<String> fileLines = Arrays.asList("123", "abc", "1111111111");
        try {
            validator.validate(fileLines);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            String errorMessage = e.getMessage();

            assertThat(errorMessage).contains("has invalid format: 123");
            assertThat(errorMessage).contains("has invalid format: abc");
        }
    }
}
