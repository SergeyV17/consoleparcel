package ru.liga.parcelmanager.processor.output;

import ru.liga.parcelmanager.model.entity.Truck;

import java.util.List;

public interface OutputProcessor {

    void writeTrucks(List<Truck> trucks);
    void writeParcels(List<String> parcels);
}