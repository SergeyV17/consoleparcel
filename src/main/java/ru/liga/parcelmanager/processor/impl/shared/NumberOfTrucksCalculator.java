package ru.liga.parcelmanager.processor.impl.shared;

import ru.liga.parcelmanager.model.entity.Truck;

import java.util.List;

public class NumberOfTrucksCalculator {

    private static final Integer INCREMENT_TO_ONE = 1;
    private static final Integer DIVISION_WITHOUT_REMAINDER_FLAG = 0;
    private static final Integer SEED = 0;

    public Integer calculateNumberOfTrucks(List<String> cargo) {
        Integer cargoVolume = Truck.MAX_HEIGHT * Truck.MAX_WIDTH;
        Integer parcelsVolume = cargo.stream().map(String::length).reduce(SEED, Integer::sum);

        int numberOfTrucks = parcelsVolume / cargoVolume;
        if (parcelsVolume % cargoVolume != DIVISION_WITHOUT_REMAINDER_FLAG) {
            numberOfTrucks += INCREMENT_TO_ONE;
        }
        return numberOfTrucks;
    }
}
