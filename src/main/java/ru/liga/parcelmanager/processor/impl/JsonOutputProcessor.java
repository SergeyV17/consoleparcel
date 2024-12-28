package ru.liga.parcelmanager.processor.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.liga.parcelmanager.model.Output;
import ru.liga.parcelmanager.processor.OutputProcessor;
import ru.liga.parcelmanager.shared.Constants;

import java.io.File;
import java.io.IOException;

@Slf4j
public class JsonOutputProcessor implements OutputProcessor {

    @Override
    public void write(Output<?> output) {
        try {
            String baseDir = System.getProperty(Constants.BaseDirectory);
            File outputFile = new File(baseDir, output.getFilePath() + ".json");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, output.getValues());
        }
        catch (IOException ex) {
            log.error("An IO exception occurred when writing to json {}", ex.getMessage());
        }
    }
}
