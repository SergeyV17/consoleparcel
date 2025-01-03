package ru.liga.parcelmanager.service;

import ru.liga.parcelmanager.model.entity.Truck;
import ru.liga.parcelmanager.model.enums.LoadingMode;
import ru.liga.parcelmanager.processor.impl.shared.NumberOfTrucksCalculator;

import java.util.List;

public class TruckValidationService {

    private static final int SEED = 0;

    public void validateNumberOfTrucks(Integer numberOfTrucks, List<String> parcels, LoadingMode mode) {
        switch (mode) {
            case LOADING_TO_CAPACITY -> {
                int capacity = Truck.MAX_HEIGHT * Truck.MAX_WIDTH * numberOfTrucks;
                Integer parcelsVolume = calculateParcelsVolume(parcels);
                if (capacity < parcelsVolume) {
                    throw new IllegalArgumentException(
                            String.format("Number of trucks must be greater. Current number of trucks: %1$s", numberOfTrucks));
                }
            }
            case ONE_BY_ONE -> {
                if (numberOfTrucks != parcels.size()) {
                    throw new IllegalArgumentException("Number of trucks must be equal to the number of parcels in OneByOne mode");
                }
            }
            case UNIFORM -> {
                Integer minimumNumberOfTrucks = new NumberOfTrucksCalculator().calculateNumberOfTrucks(parcels);
                if (numberOfTrucks < minimumNumberOfTrucks)
                    throw new IllegalArgumentException("Number of trucks must be greater than or equal to " + minimumNumberOfTrucks);
            }
        }
    }

    private Integer calculateParcelsVolume(List<String> parcels) {
        return parcels.stream().map(String::length).reduce(SEED, Integer::sum);
    }
}
