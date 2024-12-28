package ru.liga.parcelmanager.processor.impl;

import lombok.RequiredArgsConstructor;
import ru.liga.parcelmanager.factory.TruckFactory;
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

    public static final int START_INCLUSIVE = 0;
    private final ParcelRowsGenerator rowsGenerator;
    private final TruckFactory truckFactory;

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
        Truck truck = truckFactory.createTruck();
        truck.loadTruck(new Cargo(batch));
        return truck;
    }

    private List<List<String>> divideOnSubCollectionsByTruckMaxHeight(List<String> source) {
        return new ArrayList<>(IntStream.range(START_INCLUSIVE, source.size())
                .boxed()
                .collect(Collectors.groupingBy(
                        index -> index / Truck.MAX_HEIGHT,
                        LinkedHashMap::new,
                        Collectors.mapping(source::get, Collectors.toList())
                ))
                .values());
    }
}
