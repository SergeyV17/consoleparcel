package ru.liga.parcel.processor.loading;

import lombok.RequiredArgsConstructor;
import ru.liga.parcel.factory.TruckFactory;
import ru.liga.parcel.model.entity.Cargo;
import ru.liga.parcel.model.entity.Truck;
import ru.liga.parcel.processor.loading.shared.ParcelRowsGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class UniformLoadingProcessor implements LoadingProcessor {

    private final TruckFactory truckFactory;
    private final ParcelRowsGenerator rowsGenerator;

    @Override
    public List<Truck> loadCargoIntoTrucks(List<String> cargo, Integer numberOfTrucks) {
        HashMap<Integer, List<String>> parcelsByTruck = distributeParcelsByTruck(
                rowsGenerator.GenerateRowsCargoByMaxWidth(cargo, Truck.MAX_WIDTH),
                numberOfTrucks == null ? calculateNumberOfTrucks(cargo) : numberOfTrucks);
        return createTrucksByConcatenatedStringCarcases(parcelsByTruck);
    }

    public static int calculateNumberOfTrucks(List<String> cargo) {
        Integer cargoVolume = Truck.MAX_HEIGHT * Truck.MAX_WIDTH;
        Integer parcelsVolume = cargo.stream().map(String::length).reduce(0, Integer::sum);

        int numberOfTrucks = parcelsVolume / cargoVolume;
        if (parcelsVolume % cargoVolume != 0) {
            numberOfTrucks += 1;
        }
        return numberOfTrucks;
    }

    public static HashMap<Integer, List<String>> distributeParcelsByTruck(
            List<String> rows,
            Integer numberOfTrucks) {

        int truckSize = numberOfTrucks - 1;
        HashMap<Integer, List<String>> parcelsByTruck = new HashMap<>(truckSize);

        Integer counter = 0;
        for (String row : rows) {
            if (parcelsByTruck.containsKey(counter)) {
                parcelsByTruck.get(counter).add(row);
            } else {
                parcelsByTruck.put(counter, new ArrayList<>());
                parcelsByTruck.get(counter).add(row);
            }

            counter++;
            if (counter > truckSize) {
                counter = 0;
            }
        }
        return parcelsByTruck;
    }

    public List<Truck> createTrucksByConcatenatedStringCarcases(
            Map<Integer, List<String>> concatenatedCarcases) {

        List<Truck> trucks = new ArrayList<>();
        for (int i = 0; i < concatenatedCarcases.size(); i++) {
            trucks.add(truckFactory.createTruck(new Cargo(concatenatedCarcases.get(i))));
        }

        return trucks;
    }
}
