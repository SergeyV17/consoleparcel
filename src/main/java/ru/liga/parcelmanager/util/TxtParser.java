package ru.liga.parcelmanager.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.parcelmanager.service.FileValidationService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class TxtParser {

    private final TxtReader txtReader;
    private final FileValidationService fileValidator;

    public List<String> parseParcelsFromFile(String filePath) {
        List<String> fileLines = readParcelsFromFile(filePath);

        try {
            fileValidator.validate(fileLines);
        }
        catch (IllegalArgumentException ex) {
            log.error("An error occurred through validation: {}", ex.getMessage());
        }

        if (fileLines.isEmpty()) {
            throw new IllegalArgumentException("Parcels not found in " + filePath);
        }

        return fileLines;
    }

    private List<String> readParcelsFromFile(String filePath) {
        return txtReader.readAllLines(filePath)
                .stream()
                .filter(line -> !line.isEmpty())
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
