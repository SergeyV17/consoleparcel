package ru.liga.parcel.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.parcel.manager.LoadingProcessorManager;
import ru.liga.parcel.model.entity.Truck;
import ru.liga.parcel.model.enums.LoadingMode;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ParcelLoadingService {
    private final LoadingProcessorManager loadingProcessorManager;

    public List<Truck> loadParcelsIntoTrucks(List<String> parcels, LoadingMode mode, Integer numberOfTrucks) {
        return loadingProcessorManager.loadTrucks(parcels, mode, numberOfTrucks);
    }
}
