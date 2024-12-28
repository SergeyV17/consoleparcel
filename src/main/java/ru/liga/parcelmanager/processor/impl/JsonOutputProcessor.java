package ru.liga.parcelmanager.processor.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.liga.parcelmanager.model.entity.Truck;
import ru.liga.parcelmanager.processor.OutputProcessor;
import ru.liga.parcelmanager.shared.Constants;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
public class JsonOutputProcessor implements OutputProcessor {

    public final String TRUCKS_JSON_PATH = "trucks.json";
    public final String PARCELS_JSON_PATH = "parcels.json";

    @Override
    public void writeTrucks(List<Truck> trucks) {
        try {
            String baseDir = System.getProperty(Constants.BaseDirectory);
            File outputFile = new File(baseDir, TRUCKS_JSON_PATH);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, trucks);
        }
        catch (IOException ex) {
            log.error("An IO exception occurred when writing trucks to json {}", ex.getMessage());
        }
    }

    @Override
    public void writeParcels(List<String> parcels) {
        try {
            String baseDir = System.getProperty(Constants.BaseDirectory);
            File outputFile = new File(baseDir, PARCELS_JSON_PATH);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, parcels);
        }
        catch (IOException ex) {
            log.error("An IO exception occurred when writing parcels to json {}", ex.getMessage());
        }
    }
}
