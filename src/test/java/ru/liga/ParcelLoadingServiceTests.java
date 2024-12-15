package ru.liga;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.liga.model.entity.Truck;
import ru.liga.model.enums.LoadingMode;
import ru.liga.service.ParcelLoadingService;
import ru.liga.util.TxtParser;
import ru.liga.util.TxtReader;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ParcelLoadingServiceTests {

    @Test
    void loadParcelsIntoTrucks_loadByOneByOneMode_createTrucksWithOneParcel() {
        String filePath = "test/resources/parcels.txt";
        LoadingMode mode = LoadingMode.ONE_BY_ONE;
        ParcelLoadingService service = new ParcelLoadingService(new TxtParser(new TxtReader()));

        List<Truck> trucks = service.loadParcelsIntoTrucks(filePath, mode);

        trucks.forEach(truck -> assertThat(truck.getParcels().size()).isEqualTo(1));
    }

    @Test
    void loadParcelsIntoTrucks_loadByLoadingToCapacityMode_createTrucksWithSeveralRowsAndBottomMustBeGreater() {
        String filePath = "test/resources/parcels.txt";
        LoadingMode mode = LoadingMode.LOADING_TO_CAPACITY;
        ParcelLoadingService service = new ParcelLoadingService(new TxtParser(new TxtReader()));

        List<Truck> trucks = service.loadParcelsIntoTrucks(filePath, mode);

        trucks.forEach(truck -> {
            HashMap<Integer, String> parcels = truck.getParcels();
            for (int i = 0; i < parcels.size() - 1; i++) {
                assertThat(parcels.get(i).length()).isGreaterThan(parcels.get(i + 1).length());
            }
        });
    }
}
