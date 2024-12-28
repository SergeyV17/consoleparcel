package ru.liga.parcelmanager.processor.impl;

import lombok.extern.slf4j.Slf4j;
import ru.liga.parcelmanager.model.entity.Truck;
import ru.liga.parcelmanager.processor.OutputProcessor;
import ru.liga.parcelmanager.shared.Constants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Slf4j
public class TxtOutputProcessor implements OutputProcessor {

    public static final String TRUCKS_TXT_PATH = "trucks.txt";
    public static final int START_ARRAY_INDEX = 0;
    public static final int TRUCK_INDEX_COLLISION = 1;
    public static final String PARCELS_TXT_PATH = "parcels.txt";

    @Override
    public void writeTrucks(List<Truck> trucks) {
        String baseDir = System.getProperty(Constants.BaseDirectory);
        File outputFile = new File(baseDir, TRUCKS_TXT_PATH);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (int i = START_ARRAY_INDEX; i < trucks.size(); i++) {
                Truck truck = trucks.get(i);
                writer.write("Truck " + (i + TRUCK_INDEX_COLLISION) + ":\n");
                writer.write(truck.toString());
                writer.write("\n");
            }
        } catch (IOException ex) {
            log.error("An IO exception occurred when writing trucks to file {}", ex.getMessage());
        }
    }

    @Override
    public void writeParcels(List<String> parcels) {
        String baseDir = System.getProperty(Constants.BaseDirectory);
        File outputFile = new File(baseDir, PARCELS_TXT_PATH);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String parcel : parcels) {
                writer.write(parcel + "\n");
            }
        } catch (IOException ex) {
            log.error("An IO exception occurred when writing parcels to file {}", ex.getMessage());
        }
    }
}
