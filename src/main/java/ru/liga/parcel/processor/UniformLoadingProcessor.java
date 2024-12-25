package ru.liga.parcel.processor;

import lombok.RequiredArgsConstructor;
import ru.liga.parcel.factory.TruckFactory;
import ru.liga.parcel.model.entity.Cargo;
import ru.liga.parcel.model.entity.Truck;
import ru.liga.parcel.processor.shared.ParcelRowsGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
public class UniformLoadingProcessor implements LoadingProcessor {

    private final TruckFactory truckFactory;
    private final ParcelRowsGenerator rowsGenerator;

    @Override
    public List<Truck> loadCargoIntoTrucks(List<String> cargo) {
        HashMap<Integer, List<String>> parcelsByTruck = distributeParcelsByTruck(
                rowsGenerator.GenerateRowsCargoByMaxWidth(cargo, Truck.MAX_WIDTH),
                calculateNumberOfTrucks(cargo));
        return createTrucksByConcatenatedStringCarcases(parcelsByTruck);
    }

    private static int calculateNumberOfTrucks(List<String> cargo) {
        Integer cargoVolume = Truck.MAX_HEIGHT * Truck.MAX_WIDTH;
        Integer parcelsVolume = cargo.stream().map(String::length).reduce(0, Integer::sum);

        int numberOfTrucks = parcelsVolume / cargoVolume;
        if (parcelsVolume % cargoVolume != 0) {
            numberOfTrucks += 1;
        }
        return numberOfTrucks;
    }

    private static HashMap<Integer, List<String>> distributeParcelsByTruck(
            List<String> rows,
            Integer numberOfTrucks) {
        HashMap<Integer, List<String>> parcelsByTruck = new HashMap<>(numberOfTrucks);

        Integer counter = 0;
        for (String row : rows) {
            if (parcelsByTruck.containsKey(counter)) {
                parcelsByTruck.get(counter).add(row);
            } else {
                parcelsByTruck.put(counter, new ArrayList<>());
                parcelsByTruck.get(counter).add(row);
            }

            counter++;
            if (counter > numberOfTrucks) {
                counter = 0;
            }
        }
        return parcelsByTruck;
    }

    private List<Truck> createTrucksByConcatenatedStringCarcases(
            HashMap<Integer, List<String>> concatenatedCarcases) {

        List<Truck> trucks = new ArrayList<>();
        for (int i = 0; i < concatenatedCarcases.size(); i++) {
            trucks.add(truckFactory.createTruck(new Cargo(concatenatedCarcases.get(i))));
        }

        return trucks;
    }
}
