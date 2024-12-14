package ru.liga.util;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TxtParser {
    private final TxtReader txtReader;

    public List<String> parseCargoFromFile(String filePath) {
        return txtReader
                .readAllLines(filePath)
                .stream()
                .filter(line -> !line.isEmpty())
                .map(String::trim)
                .toList();
    }
}
