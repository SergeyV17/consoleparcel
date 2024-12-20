package ru.liga.parcel.service;

import org.junit.jupiter.api.Test;
import ru.liga.parcel.model.entity.Truck;
import ru.liga.parcel.model.enums.LoadingMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ParcelLoadingServiceTests {

    @Test
    void loadParcelsIntoTrucks_loadByOneByOneMode_createTrucksWithOneParcel() {
        ParcelLoadingService service = new ParcelLoadingService();

        List<Truck> trucks = service.loadParcelsIntoTrucks(createParcels(), LoadingMode.ONE_BY_ONE);

        trucks.forEach(truck -> assertThat(truck.getParcels().size()).isEqualTo(1));
    }

    @Test
    void loadParcelsIntoTrucks_loadByLoadingToCapacityMode_createTrucksWithSeveralRowsAndBottomMustBeGreater() {
        ParcelLoadingService service = new ParcelLoadingService();

        List<Truck> trucks = service.loadParcelsIntoTrucks(createParcels(), LoadingMode.LOADING_TO_CAPACITY);

        trucks.forEach(truck -> {
            HashMap<Integer, String> parcels = truck.getParcels();
            for (int i = 0; i < parcels.size() - 1; i++) {
                assertThat(parcels.get(i).length()).isGreaterThanOrEqualTo(parcels.get(i + 1).length());
            }
        });
    }

    private static ArrayList<String> createParcels() {
        return new ArrayList<>() {
            {
                add("1");
                add("22");
                add("333");
                add("4444");
                add("333");
                add("22");
                add("1");
            }
        };
    }
}
