package ru.liga.util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Slf4j
public class TxtReader {
    public List<String> readAllLines(String txtPath) {
        try {
            URL resource = getClass().getClassLoader().getResource(txtPath);
            if (resource != null) {
                return Files.readAllLines(Paths.get(resource.toURI()));
            }

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
