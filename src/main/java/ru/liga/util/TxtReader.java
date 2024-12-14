package ru.liga.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
public class TxtReader {
    public List<String> readAllLines(String txtPath) {
        try {
            return Files.readAllLines(
                    new File(Objects.requireNonNull(getClass()
                                    .getClassLoader()
                                    .getResource(txtPath))
                            .toURI())
                            .toPath());
        }
        catch (FileNotFoundException ex) {
            log.error("Файл {} не найден", txtPath, ex);
            return Collections.emptyList();
        }
        catch (Exception ex) {
            log.error("Невозможно прочитать файл {}", txtPath, ex);
            return Collections.emptyList();
        }
    }
}
