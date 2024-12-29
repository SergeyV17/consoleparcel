package ru.liga.parcelmanager.util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Slf4j
public class TxtReader {

    public List<String> readAllLines(String txtPath) {
        try {
            return Files.readAllLines(Paths.get(txtPath));
        }
        catch (FileNotFoundException ex) {
            log.error("File {} not found", txtPath, ex);
            return Collections.emptyList();
        }
        catch (Exception ex) {
            log.error("Can't read file {}", txtPath, ex);
            return Collections.emptyList();
        }
    }
}
