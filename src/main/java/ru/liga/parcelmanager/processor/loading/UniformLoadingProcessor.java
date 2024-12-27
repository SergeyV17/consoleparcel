package ru.liga.parcelmanager.processor.loading;

import lombok.RequiredArgsConstructor;
import ru.liga.parcelmanager.factory.TruckFactory;
import ru.liga.parcelmanager.model.entity.Cargo;
import ru.liga.parcelmanager.model.entity.Truck;
import ru.liga.parcelmanager.processor.loading.shared.ParcelRowsGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class UniformLoadingProcessor implements LoadingProcessor {

    public static final Integer DIVISION_WITHOUT_REMAINDER_FLAG = 0;
    public static final int TO_ARRAY_LAST_INDEX = 1;
    public static final int START_COUNTER_VALUE = 0;
    public static final int START_ARRAY_INDEX = 0;
    private final TruckFactory truckFactory;
    private final ParcelRowsGenerator rowsGenerator;

    @Override
    public List<Truck> loadCargosIntoTrucks(List<String> cargo, Integer numberOfTrucks) {
        Map<Integer, List<String>> parcelsByTruck = distributeParcelsByTruck(
                rowsGenerator.generateRowsCargoByMaxWidth(cargo, Truck.MAX_WIDTH),
                numberOfTrucks == null ? calculateNumberOfTrucks(cargo) : numberOfTrucks);
        return createTrucksByConcatenatedStringCarcases(parcelsByTruck);
    }

    public Integer calculateNumberOfTrucks(List<String> cargo) {
        Integer cargoVolume = Truck.MAX_HEIGHT * Truck.MAX_WIDTH;
        Integer parcelsVolume = cargo.stream().map(String::length).reduce(0, Integer::sum);

        Integer numberOfTrucks = parcelsVolume / cargoVolume;
        if (parcelsVolume % cargoVolume != DIVISION_WITHOUT_REMAINDER_FLAG) {
            numberOfTrucks += 1;
        }
        return numberOfTrucks;
    }

    public Map<Integer, List<String>> distributeParcelsByTruck(
            List<String> rows,
            Integer numberOfTrucks) {

        Integer truckSize = numberOfTrucks - TO_ARRAY_LAST_INDEX;
        Map<Integer, List<String>> parcelsByTruck = new HashMap<>(truckSize);

        Integer counter = 0;
        for (String row : rows) {
            if (parcelsByTruck.containsKey(counter)) {
                parcelsByTruck.get(counter).add(row);
            } else {
                parcelsByTruck.put(counter, new ArrayList<>());
                parcelsByTruck.get(counter).add(row);
            }

            counter++;
            counter = resetCounterIfMoreThenTrackSize(counter, truckSize);
        }
        return parcelsByTruck;
    }

    public List<Truck> createTrucksByConcatenatedStringCarcases(
            Map<Integer, List<String>> concatenatedCarcases) {

        List<Truck> trucks = new ArrayList<>();
        for (Integer i = START_ARRAY_INDEX; i < concatenatedCarcases.size(); i++) {
            trucks.add(truckFactory.createTruck(new Cargo(concatenatedCarcases.get(i))));
        }

        return trucks;
    }

    private Integer resetCounterIfMoreThenTrackSize(Integer counter, Integer truckSize) {
        if (counter > truckSize) {
            counter = START_COUNTER_VALUE;
        }
        return counter;
    }
}
