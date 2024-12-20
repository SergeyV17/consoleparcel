package ru.liga.parcel.validation;

import org.junit.jupiter.api.Test;
import ru.liga.parcel.model.entity.Truck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.Fail.fail;

public class FileValidatorTests {
    @Test
    void validateFileLines_EmptyFileLines_ThrowsIllegalArgumentException() {
        var validator = new FileValidator();
        List<String> fileLines = new ArrayList<>();
        assertThatCode(() -> validator.validateFileLines(fileLines)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void validateFileLines_ValidFileLines_NoErrors() {
        var validator = new FileValidator();
        List<String> fileLines = Arrays.asList("111", "222", "333");
        validator.validateFileLines(fileLines);
    }

    @Test
    void validateFileLines_InvalidFileLines_ThrowsIllegalArgumentException() {
        var validator = new FileValidator();
        List<String> fileLines = Arrays.asList("123", "abc", "456");
        assertThatCode(() -> validator.validateFileLines(fileLines)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void validateFileLines_FileLinesTooLong_ThrowsIllegalArgumentException() {
        var validator = new FileValidator();
        List<String> fileLines = List.of("1111111111");
        assertThatCode(() -> validator.validateFileLines(fileLines)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void validateFileLines_MultipleErrors_ThrowsIllegalArgumentExceptionWithMultipleErrors() {
        var validator = new FileValidator();
        List<String> fileLines = Arrays.asList("123", "abc", "1111111111");
        try {
            validator.validateFileLines(fileLines);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            String errorMessage = e.getMessage();

            assertThat(errorMessage).contains("File line has invalid format: 123");
            assertThat(errorMessage).contains("File line has invalid format: abc");
            assertThat(errorMessage).contains("Parcel 1111111111 has invalid width. Max available width: " + Truck.MAX_WIDTH);
        }
    }
}
