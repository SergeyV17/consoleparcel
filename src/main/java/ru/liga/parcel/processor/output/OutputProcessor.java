package ru.liga.parcel.processor.output;

import ru.liga.parcel.model.entity.Truck;

import java.util.List;

public interface OutputProcessor {
    void writeTrucks(List<Truck> trucks);
    void writeParcels(List<String> parcels);
}
