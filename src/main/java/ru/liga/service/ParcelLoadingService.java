package ru.liga.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.entity.Truck;
import ru.liga.model.enums.LoadingMode;
import ru.liga.util.TxtParser;

import java.util.Collections;
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
            // TODO реализовать
        }
        else if (mode == LoadingMode.ONE_BY_ONE) {
            return parcels.stream().map(Truck::new).toList();
        }

        return Collections.emptyList();
    }
}
