package ru.liga.parcel.processor.output;

import lombok.extern.slf4j.Slf4j;
import ru.liga.parcel.model.entity.Truck;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Slf4j
public class TxtOutputProcessor implements OutputProcessor {
    @Override
    public void writeTrucks(List<Truck> trucks) {
        String baseDir = System.getProperty("user.dir");
        File outputFile = new File(baseDir, "trucks.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (int i = 0; i < trucks.size(); i++) {
                Truck truck = trucks.get(i);
                writer.write("Truck " + (i + 1) + ":\n");
                writer.write(truck.toString());
                writer.write("\n");
            }
        } catch (IOException ex) {
            log.error("An IO exception occurred when writing trucks to file {}", ex.getMessage());
        }
    }

    @Override
    public void writeParcels(List<String> parcels) {
        String baseDir = System.getProperty("user.dir");
        File outputFile = new File(baseDir, "parcels.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String parcel : parcels) {
                writer.write(parcel + "\n");
            }
        } catch (IOException ex) {
            log.error("An IO exception occurred when writing parcels to file {}", ex.getMessage());
        }
    }
}
