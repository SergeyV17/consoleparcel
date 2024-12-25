package ru.liga.parcel.processor.loading;

import ru.liga.parcel.model.entity.Truck;

import java.util.List;

public interface LoadingProcessor {
    List<Truck> loadCargoIntoTrucks(List<String> cargos, Integer numberOfTrucks);
}
