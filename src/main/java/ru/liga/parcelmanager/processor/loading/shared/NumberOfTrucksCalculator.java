package ru.liga.parcelmanager.processor.loading.shared;

import ru.liga.parcelmanager.model.entity.Truck;

import java.util.List;

public class NumberOfTrucksCalculator {

    private static final Integer DIVISION_WITHOUT_REMAINDER_FLAG = 0;

    public Integer calculateNumberOfTrucks(List<String> cargo) {
        Integer cargoVolume = Truck.MAX_HEIGHT * Truck.MAX_WIDTH;
        Integer parcelsVolume = cargo.stream().map(String::length).reduce(0, Integer::sum);

        int numberOfTrucks = parcelsVolume / cargoVolume;
        if (parcelsVolume % cargoVolume != DIVISION_WITHOUT_REMAINDER_FLAG) {
            numberOfTrucks += 1;
        }
        return numberOfTrucks;
    }
}
