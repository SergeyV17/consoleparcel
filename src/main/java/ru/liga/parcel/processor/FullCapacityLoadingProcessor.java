package ru.liga.parcel.processor;

import lombok.RequiredArgsConstructor;
import ru.liga.parcel.factory.TruckFactory;
import ru.liga.parcel.model.entity.Cargo;
import ru.liga.parcel.model.entity.Truck;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        return groupIndicesByTruckMaxHeight(source.size())
                .values().stream()
                .map(indices -> mapIndicesToElements(source, indices))
                .collect(Collectors.toList());
    }

    private Map<Integer, List<Integer>> groupIndicesByTruckMaxHeight(int size) {
        return IntStream.range(0, size)
                .boxed()
                .collect(Collectors.groupingBy(this::getTruckMaxHeightGroups));
    }

    private int getTruckMaxHeightGroups(int size) {
        return size / Truck.MAX_HEIGHT;
    }

    private <T> List<T> mapIndicesToElements(List<T> source, List<Integer> indices) {
        return indices.stream()
                .map(source::get)
                .collect(Collectors.toList());
    }
}
