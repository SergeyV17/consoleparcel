package ru.liga.parcel.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.liga.parcel.model.entity.Truck;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
public class JsonParser {
    public List<Truck> parseTrucksFromJson(String filePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new File(filePath), new TypeReference<>() { });
        } catch (IOException ex) {
            log.error("An IO exception occurred {}", ex.getMessage());
        }

        return null;
    }
}
