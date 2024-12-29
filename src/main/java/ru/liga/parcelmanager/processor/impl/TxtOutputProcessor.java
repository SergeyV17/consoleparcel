package ru.liga.parcelmanager.processor.impl;

import lombok.extern.slf4j.Slf4j;
import ru.liga.parcelmanager.model.Output;
import ru.liga.parcelmanager.processor.OutputProcessor;
import ru.liga.parcelmanager.shared.Constants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
public class TxtOutputProcessor implements OutputProcessor {

    public static final String TXT_EXTENSION = ".txt";

    @Override
    public void write(Output<?> output) {
        String baseDir = System.getProperty(Constants.BaseDirectory);
        File outputFile = new File(baseDir, output.getFilePath() + TXT_EXTENSION);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String value : output.getValues().stream().map(Object::toString).toList()) {
                writer.write(value + "\n");
            }
        } catch (IOException ex) {
            log.error("An IO exception occurred when writing to txt file {}", ex.getMessage());
        }
    }
}
