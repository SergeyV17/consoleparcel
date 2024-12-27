package ru.liga.parcelmanager.validation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CommandValidatorTest {

    @Test
    public void validateLoadTruckCommandAndGetFilePath_ValidTxtFile_ReturnsFilePath() {
        String command = "path/to/file.txt";
        CommandValidator validator = new CommandValidator();

        String filePath = validator.validateLoadTruckCommandAndGetFilePath(command);

        assertThat(filePath).isEqualTo("path/to/file.txt");
    }

    @Test
    public void validateLoadTruckCommandAndGetFilePath_InvalidTxtFile_ThrowsIllegalArgumentException() {
        String command = "path/to/file.pdf";
        CommandValidator validator = new CommandValidator();

        assertThatThrownBy(() -> validator.validateLoadTruckCommandAndGetFilePath(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid txt file: path/to/file.pdf");
    }

    @Test
    public void validateUnloadTruckCommandAndGetFilePath_ValidJsonFile_ReturnsFilePath() {
        String command = "path/to/file.json";
        CommandValidator validator = new CommandValidator();

        String filePath = validator.validateUnloadTruckCommandAndGetFilePath(command);

        assertThat(filePath).isEqualTo("path/to/file.json");
    }

    @Test
    public void validateUnloadTruckCommandAndGetFilePath_InvalidJsonFile_ThrowsIllegalArgumentException() {
        String command = "path/to/file.xml";
        CommandValidator validator = new CommandValidator();

        assertThatThrownBy(() -> validator.validateUnloadTruckCommandAndGetFilePath(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid json file: path/to/file.xml");
    }
}