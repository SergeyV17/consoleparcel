package ru.liga.parcelmanager.processor.impl;

import lombok.extern.slf4j.Slf4j;
import ru.liga.parcelmanager.model.entity.Truck;
import ru.liga.parcelmanager.processor.OutputProcessor;

import java.util.List;

@Slf4j
public class ConsoleOutputProcessor implements OutputProcessor {

    @Override
    public void writeTrucks(List<Truck> trucks) {
        for (Truck truck : trucks) {
            log.info(truck.toString());
        }
    }

    @Override
    public void writeParcels(List<String> parcels) {
        for (String parcel : parcels) {
            log.info(parcel);
        }
    }
}
