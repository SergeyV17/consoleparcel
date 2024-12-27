package ru.liga.parcelmanager.processor.loading;

import ru.liga.parcelmanager.model.entity.Truck;

import java.util.List;

public interface LoadingProcessor {

    List<Truck> loadCargosIntoTrucks(List<String> cargos, Integer numberOfTrucks);
}
