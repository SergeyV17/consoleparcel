package ru.liga.parcel.processor;

import lombok.RequiredArgsConstructor;
import ru.liga.parcel.factory.TruckFactory;
import ru.liga.parcel.model.entity.Cargo;
import ru.liga.parcel.model.entity.Truck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class FullCapacityLoadingProcessor implements LoadingProcessor {
    private final TruckFactory truckFactory;

    @Override
    public List<Truck> loadCargoIntoTrucks(List<String> cargo) {
        var parcelsCopy = new ArrayList<>(cargo);
        var concatenatedCarcases = new ArrayList<String>();
        for (int currentWidth = Truck.MAX_WIDTH; currentWidth > 0; currentWidth--) {
            for (int i = 0; i < parcelsCopy.size(); i++) {

                var rowBuffer = parcelsCopy.get(i);

                if (rowBuffer.length() == currentWidth) {
                    concatenatedCarcases.add(parcelsCopy.get(i));
                    parcelsCopy.set(i, "");
                    continue;
                }

                for (int j = i + 1; j < parcelsCopy.size(); j++) {

                    var right = parcelsCopy.get(j);

                    if (rowBuffer.length() + right.length() == currentWidth) {
                        rowBuffer += right;
                        parcelsCopy.set(i, "");
                        parcelsCopy.set(j, "");
                        break;
                    }
                }

                if (rowBuffer.length() == currentWidth) {
                    concatenatedCarcases.add(rowBuffer);
                }
            }
        }

        return createTrucksByConcatenatedStringCarcases(concatenatedCarcases);
    }

    private List<Truck> createTrucksByConcatenatedStringCarcases(ArrayList<String> concatenatedCarcases) {
        return divideOnSubCollectionsByTruckMaxHeight(concatenatedCarcases)
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
