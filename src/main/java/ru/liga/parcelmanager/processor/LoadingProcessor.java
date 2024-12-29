package ru.liga.parcelmanager.processor;

import ru.liga.parcelmanager.model.entity.Truck;

import java.util.List;

public interface LoadingProcessor {

    List<Truck> loadParcelsIntoTrucks(List<String> parcels, Integer numberOfTrucks);
}
