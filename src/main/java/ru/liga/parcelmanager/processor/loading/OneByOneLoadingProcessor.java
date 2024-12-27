package ru.liga.parcelmanager.processor.loading;

import lombok.RequiredArgsConstructor;
import ru.liga.parcelmanager.factory.TruckFactory;
import ru.liga.parcelmanager.model.entity.Cargo;
import ru.liga.parcelmanager.model.entity.Truck;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class OneByOneLoadingProcessor implements LoadingProcessor {

    private final TruckFactory truckFactory;

    @Override
    public List<Truck> loadCargosIntoTrucks(List<String> cargos, Integer numberOfTrucks) {
        return cargos.stream()
                .map(cargo -> truckFactory.createTruck(
                        new Cargo(new ArrayList<>() {{add(cargo);}})))
                .toList();
    }
}
