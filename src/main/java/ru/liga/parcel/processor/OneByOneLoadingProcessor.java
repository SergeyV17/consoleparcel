package ru.liga.parcel.processor;

import lombok.RequiredArgsConstructor;
import ru.liga.parcel.factory.TruckFactory;
import ru.liga.parcel.model.entity.Cargo;
import ru.liga.parcel.model.entity.Truck;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class OneByOneLoadingProcessor implements LoadingProcessor {
    private final TruckFactory truckFactory;

    @Override
    public List<Truck> loadCargoIntoTrucks(List<String> cargos, Integer numberOfTrucks) {
        return cargos.stream()
                .map(cargo -> truckFactory.createTruck(
                        new Cargo(new ArrayList<>() {{add(cargo);}})))
                .toList();
    }
}
