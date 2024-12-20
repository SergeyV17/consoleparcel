package ru.liga.parcel.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.parcel.validation.FileValidator;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class TxtParser {
    private final TxtReader txtReader;

    private final FileValidator fileValidator;

    public List<String> parseCargoFromFile(String filePath) {
        List<String> fileLines = txtReader.readAllLines(filePath)
                .stream()
                .filter(line -> !line.isEmpty())
                .map(String::trim)
                .collect(Collectors.toList());

        try {
            fileValidator.validateFileLines(fileLines);
        }
        catch (IllegalArgumentException ex) {
            log.error("An error occurred through validation: {}", ex.getMessage());
        }

        return fileLines;
    }
}
