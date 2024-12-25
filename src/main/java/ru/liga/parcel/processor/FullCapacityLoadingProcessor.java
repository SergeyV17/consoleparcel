package ru.liga.parcel.processor;

import lombok.RequiredArgsConstructor;
import ru.liga.parcel.factory.TruckFactory;
import ru.liga.parcel.model.entity.Cargo;
import ru.liga.parcel.model.entity.Truck;
import ru.liga.parcel.processor.shared.ParcelRowsGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class FullCapacityLoadingProcessor implements LoadingProcessor {
    private final TruckFactory truckFactory;
    private final ParcelRowsGenerator rowsGenerator;

    @Override
    public List<Truck> loadCargoIntoTrucks(List<String> cargo) {
        return createTrucksByParcelRows(rowsGenerator.GenerateRowsCargoByMaxWidth(cargo, Truck.MAX_WIDTH));
    }

    private List<Truck> createTrucksByParcelRows(List<String> rows) {
        return divideOnSubCollectionsByTruckMaxHeight(rows)
                .stream()
                .map(batch -> truckFactory.createTruck(new Cargo(batch)))
                .toList();
    }

    private <T> List<List<T>> divideOnSubCollectionsByTruckMaxHeight(List<T> source) {
        return new ArrayList<>(IntStream.range(0, source.size())
                .boxed()
                .collect(Collectors.groupingBy(
                        index -> index / Truck.MAX_HEIGHT,
                        LinkedHashMap::new,
                        Collectors.mapping(source::get, Collectors.toList())
                ))
                .values());
    }
}
