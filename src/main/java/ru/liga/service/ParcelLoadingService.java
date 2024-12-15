package ru.liga.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.model.entity.Truck;
import ru.liga.model.enums.LoadingMode;
import ru.liga.util.ListExtensions;
import ru.liga.util.TxtParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ParcelLoadingService {
    private final TxtParser txtParser;

    public List<Truck> loadParcelsIntoTrucks(String filePath, LoadingMode mode) {
        var parcels = txtParser.parseCargoFromFile(filePath);
        if (parcels.isEmpty()) {
            log.error("Parcels not found in {}", filePath);
            return Collections.emptyList();
        }

        if (mode == LoadingMode.LOADING_TO_CAPACITY) {
            return getTrucksByLoadingToCapacity(parcels);
        }
        else if (mode == LoadingMode.ONE_BY_ONE) {
            return parcels.stream().map(Truck::new).toList();
        }

        return Collections.emptyList();
    }

    private static List<Truck> getTrucksByLoadingToCapacity(List<String> parcels) {
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

        return ListExtensions.tile(concatenatedCarcases, Truck.MAX_HEIGHT)
                .stream().map(batch -> {
                    var map = new HashMap<Integer, String>();
                    for (int i = 0; i < batch.size(); i++) {
                        map.put(i, batch.get(i));
                    }

                    return map;
                })
                .map(Truck::new)
                .toList();
    }
}
