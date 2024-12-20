package ru.liga.parcel.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.parcel.factory.TruckFactory;
import ru.liga.parcel.model.entity.Truck;
import ru.liga.parcel.model.enums.LoadingMode;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ParcelLoadingService {

    private final TruckFactory truckFactory;

    public List<Truck> loadParcelsIntoTrucks(List<String> parcels, LoadingMode mode) {
        if (mode == LoadingMode.LOADING_TO_CAPACITY) {
            return truckFactory.CreateTrucksLoadingToCapacity(parcels);
        }
        else if (mode == LoadingMode.ONE_BY_ONE) {
            return truckFactory.CreateTrucksOneByOne(parcels);
        }
        else {
            throw new IllegalStateException("Invalid loading mode: " + mode);
        }
    }
}
