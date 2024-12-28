package ru.liga.parcelmanager.processor.impl;

import lombok.RequiredArgsConstructor;
import ru.liga.parcelmanager.model.entity.Cargo;
import ru.liga.parcelmanager.model.entity.Truck;
import ru.liga.parcelmanager.processor.LoadingProcessor;
import ru.liga.parcelmanager.processor.impl.shared.ParcelRowsGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class FullCapacityLoadingProcessor implements LoadingProcessor {

    private final ParcelRowsGenerator rowsGenerator;

    @Override
    public List<Truck> loadParcelsIntoTrucks(List<String> cargo, Integer numberOfTrucks) {
        return createTrucksByParcelRows(rowsGenerator.generateRowsCargoByMaxWidth(cargo, Truck.MAX_WIDTH));
    }

    private List<Truck> createTrucksByParcelRows(List<String> rows) {
        return divideOnSubCollectionsByTruckMaxHeight(rows)
                .stream()
                .map(this::createAndLoadTruck)
                .toList();
    }

    private Truck createAndLoadTruck(List<String> batch) {
        Truck truck = new Truck();
        truck.loadTruck(new Cargo(batch));
        return truck;
    }

    private List<List<String>> divideOnSubCollectionsByTruckMaxHeight(List<String> source) {
        int SEED_VALUE = 0;
        return new ArrayList<>(IntStream.range(SEED_VALUE, source.size())
                .boxed()
                .collect(Collectors.groupingBy(
                        index -> index / Truck.MAX_HEIGHT,
                        LinkedHashMap::new,
                        Collectors.mapping(source::get, Collectors.toList())
                ))
                .values());
    }
}
