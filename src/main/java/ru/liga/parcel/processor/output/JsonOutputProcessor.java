package ru.liga.parcel.processor.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.liga.parcel.model.entity.Truck;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
public class JsonOutputProcessor implements OutputProcessor {
    @Override
    public void writeTrucks(List<Truck> trucks) {
        try {
            String baseDir = System.getProperty("user.dir");
            File outputFile = new File(baseDir, "trucks.json");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, trucks);
        }
        catch (IOException ex) {
            log.error("An IO exception occurred {}", ex.getMessage());
        }
    }
}
