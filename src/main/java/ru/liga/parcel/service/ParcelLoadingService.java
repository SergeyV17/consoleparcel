package ru.liga.parcel.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.parcel.model.entity.Truck;
import ru.liga.parcel.model.enums.LoadingMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
public class ParcelLoadingService {
    public List<Truck> loadParcelsIntoTrucks(List<String> parcels, LoadingMode mode) {
        if (mode == LoadingMode.LOADING_TO_CAPACITY) {
            return createListOfTrucksLoadedToCapacity(parcels);
        }
        else if (mode == LoadingMode.ONE_BY_ONE) {
            return parcels.stream().map(Truck::new).toList();
        }
        else {
            throw new IllegalStateException("Invalid loading mode: " + mode);
        }
    }

    private List<Truck> createListOfTrucksLoadedToCapacity(List<String> parcels) {
        var concatenatedCarcases = new ArrayList<String>();

        for (int currentWidth = Truck.MAX_WIDTH; currentWidth > 0 ; currentWidth--) {
            for (int i = 0; i < parcels.size(); i++) {

                var rowBuffer = parcels.get(i);

                if (rowBuffer.length() == currentWidth) {
                    concatenatedCarcases.add(parcels.get(i));
                    parcels.set(i, "");
                    continue;
                }

                for (int j = i + 1; j < parcels.size(); j++) {

                    var right = parcels.get(j);

                    if (rowBuffer.length() + right.length() == currentWidth) {
                        rowBuffer += right;
                        parcels.set(i, "");
                        parcels.set(j, "");
                        break;
                    }
                }

                if (rowBuffer.length() == currentWidth) {
                    concatenatedCarcases.add(rowBuffer);
                }
            }
        }

        return divideOnSubCollectionsByMaxHeight(concatenatedCarcases)
                .stream()
                .map(batch -> {
                    var map = new HashMap<Integer, String>();
                    for (int i = 0; i < batch.size(); i++) {
                        map.put(i, batch.get(i));
                    }

                    return map;
                })
                .map(Truck::new)
                .toList();
    }

    private  <T> List<List<T>> divideOnSubCollectionsByMaxHeight(List<T> source) {
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
