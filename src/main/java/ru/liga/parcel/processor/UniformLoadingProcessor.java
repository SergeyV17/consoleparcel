package ru.liga.parcel.processor;

import ru.liga.parcel.model.entity.Truck;

import java.util.List;

public class UniformLoadingProcessor implements LoadingProcessor {
    @Override
    public List<Truck> loadCargoIntoTrucks(List<String> cargos) {
        return List.of(); // TODO реализовать алгоритм
    }
}
