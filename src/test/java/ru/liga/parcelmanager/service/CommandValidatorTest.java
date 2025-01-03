package ru.liga.parcelmanager.service;

import org.junit.jupiter.api.Test;
import ru.liga.parcelmanager.exceptions.InvalidJsonException;
import ru.liga.parcelmanager.exceptions.InvalidTxtException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CommandValidatorTest {

    @Test
    public void validateLoadTruckCommandAndGetFilePath_ValidTxtFile_ReturnsFilePath() {
        String command = "path/to/file.txt";
        CommandValidationService validator = new CommandValidationService();

        String filePath = validator.validateLoadTruckCommandAndGetFilePath(command);

        assertThat(filePath).isEqualTo("path/to/file.txt");
    }

    @Test
    public void validateLoadTruckCommandAndGetFilePath_InvalidTxtFile_ThrowsIllegalArgumentException() {
        String command = "path/to/file.pdf";
        CommandValidationService validator = new CommandValidationService();

        assertThatThrownBy(() -> validator.validateLoadTruckCommandAndGetFilePath(command))
                .isInstanceOf(InvalidTxtException.class)
                .hasMessage("path/to/file.pdf");
    }

    @Test
    public void validateUnloadTruckCommandAndGetFilePath_ValidJsonFile_ReturnsFilePath() {
        String command = "path/to/file.json";
        CommandValidationService validator = new CommandValidationService();

        String filePath = validator.validateUnloadTruckCommandAndGetFilePath(command);

        assertThat(filePath).isEqualTo("path/to/file.json");
    }

    @Test
    public void validateUnloadTruckCommandAndGetFilePath_InvalidJsonFile_ThrowsIllegalArgumentException() {
        String command = "path/to/file.xml";
        CommandValidationService validator = new CommandValidationService();

        assertThatThrownBy(() -> validator.validateUnloadTruckCommandAndGetFilePath(command))
                .isInstanceOf(InvalidJsonException.class)
                .hasMessage("path/to/file.xml");
    }
}