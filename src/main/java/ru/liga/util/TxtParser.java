package ru.liga.util;

import lombok.RequiredArgsConstructor;
import ru.liga.validation.FileValidator;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TxtParser {
    private final TxtReader txtReader;

    private final FileValidator fileValidator;

    public List<String> parseCargoFromFile(String filePath) {
        var fileLines = txtReader.readAllLines(filePath)
                .stream()
                .filter(line -> !line.isEmpty())
                .map(String::trim)
                .collect(Collectors.toList());

        fileValidator.validateFileLines(fileLines);

        return fileLines;
    }
}
