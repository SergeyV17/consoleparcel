package ru.liga.parcelmanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.parcelmanager.model.entity.Truck;
import ru.liga.parcelmanager.model.enums.LoadingMode;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ParcelLoadingService {

    private final LoadingProcessorService loadingProcessorManager;

    public List<Truck> loadParcelsIntoTrucks(List<String> parcels, LoadingMode mode, Integer numberOfTrucks) {
        return loadingProcessorManager.loadTrucks(parcels, mode, numberOfTrucks);
    }
}
