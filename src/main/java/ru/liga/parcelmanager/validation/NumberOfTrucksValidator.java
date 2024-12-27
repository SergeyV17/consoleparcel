package ru.liga.parcelmanager.validation;

import ru.liga.parcelmanager.model.entity.Truck;
import ru.liga.parcelmanager.model.enums.LoadingMode;
import ru.liga.parcelmanager.processor.loading.UniformLoadingProcessor;

import java.util.List;

public class NumberOfTrucksValidator {

    public static final int Seed = 0;

    public void validate(Integer numberOfTrucks, List<String> parcels, LoadingMode mode) {
        switch (mode) {
            case LOADING_TO_CAPACITY -> {
                Integer capacity = Truck.MAX_HEIGHT * Truck.MAX_WIDTH * numberOfTrucks;
                Integer parcelsVolume = parcels.stream().map(String::length).reduce(Seed, Integer::sum);
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
                Integer minimumNumberOfTrucks = UniformLoadingProcessor.calculateNumberOfTrucks(parcels);
                if (numberOfTrucks < minimumNumberOfTrucks)
                    throw new IllegalArgumentException("Number of trucks must be greater than or equal to " + minimumNumberOfTrucks);
            }
        }
    }
}
