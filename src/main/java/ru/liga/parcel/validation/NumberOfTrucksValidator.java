package ru.liga.parcel.validation;

import ru.liga.parcel.model.entity.Truck;
import ru.liga.parcel.model.enums.LoadingMode;
import ru.liga.parcel.processor.loading.UniformLoadingProcessor;

import java.util.List;

public class NumberOfTrucksValidator {
    public void validate(int numberOfTrucks, List<String> parcels, LoadingMode mode) {
        switch (mode) {
            case LOADING_TO_CAPACITY -> {
                int capacity = Truck.MAX_HEIGHT * Truck.MAX_WIDTH * numberOfTrucks;
                if (capacity < parcels.stream().map(String::length).reduce(0, Integer::sum)) {
                    throw new IllegalArgumentException("Number of trucks must be greater");
                }
            }
            case ONE_BY_ONE -> {
                if (numberOfTrucks != parcels.size()) {
                    throw new IllegalArgumentException("Number of trucks must be equal to the number of parcels in OneByOne mode");
                }
            }
            case UNIFORM -> {
                int minimumNumberOfTrucks = UniformLoadingProcessor.calculateNumberOfTrucks(parcels);
                if (numberOfTrucks < minimumNumberOfTrucks)
                    throw new IllegalArgumentException("Number of trucks must be greater than or equal to " + minimumNumberOfTrucks);
            }
        }
    }
}
