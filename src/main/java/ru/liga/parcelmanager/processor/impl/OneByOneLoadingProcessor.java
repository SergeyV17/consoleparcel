package ru.liga.parcelmanager.processor.impl;

import lombok.RequiredArgsConstructor;
import ru.liga.parcelmanager.model.entity.Cargo;
import ru.liga.parcelmanager.model.entity.Truck;
import ru.liga.parcelmanager.processor.LoadingProcessor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class OneByOneLoadingProcessor implements LoadingProcessor {

    @Override
    public List<Truck> loadParcelsIntoTrucks(List<String> cargos, Integer numberOfTrucks) {
        return cargos.stream()
                .map(this::createAndLoadTruck)
                .toList();
    }

    private Truck createAndLoadTruck(String cargo) {
        Truck truck = new Truck();
        truck.loadTruck(new Cargo(new ArrayList<>() {{add(cargo);}}));
        return truck;
    }
}
