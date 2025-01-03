package ru.liga.parcelmanager.processor.impl;

import lombok.RequiredArgsConstructor;
import ru.liga.parcelmanager.factory.TruckFactory;
import ru.liga.parcelmanager.model.entity.Cargo;
import ru.liga.parcelmanager.model.entity.Truck;
import ru.liga.parcelmanager.processor.LoadingProcessor;
import ru.liga.parcelmanager.processor.impl.shared.NumberOfTrucksCalculator;
import ru.liga.parcelmanager.processor.impl.shared.ParcelRowsGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class UniformLoadingProcessor implements LoadingProcessor {

    public static final int TO_ARRAY_LAST_INDEX = 1;
    public static final int START_COUNTER_VALUE = 0;
    public static final int START_ARRAY_INDEX = 0;

    private final ParcelRowsGenerator rowsGenerator;
    private final NumberOfTrucksCalculator numberOfTrucksCalculator;
    private final TruckFactory truckFactory;

    @Override
    public List<Truck> loadParcelsIntoTrucks(List<String> parcels, Integer numberOfTrucks) {
        Map<Integer, List<String>> parcelsByTruck = distributeParcelsByTruck(
                rowsGenerator.generateRowsCargoByMaxWidth(parcels, Truck.MAX_WIDTH),
                numberOfTrucks == null ? numberOfTrucksCalculator.calculateNumberOfTrucks(parcels) : numberOfTrucks);
        return createTrucksByConcatenatedStringCarcases(parcelsByTruck);
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
            counter = resetCounter(counter, truckSize);
        }
        return parcelsByTruck;
    }

    public List<Truck> createTrucksByConcatenatedStringCarcases(
            Map<Integer, List<String>> concatenatedCarcases) {

        List<Truck> trucks = new ArrayList<>();
        for (int i = START_ARRAY_INDEX; i < concatenatedCarcases.size(); i++) {
            Truck truck = truckFactory.createTruck();
            truck.loadTruck(new Cargo(concatenatedCarcases.get(i)));
            trucks.add(truck);
        }

        return trucks;
    }

    private Integer resetCounter(Integer counter, Integer truckSize) {
        if (counter > truckSize) {
            counter = START_COUNTER_VALUE;
        }
        return counter;
    }
}
