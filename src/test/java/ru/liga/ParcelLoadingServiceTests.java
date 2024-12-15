package ru.liga;

import org.junit.jupiter.api.Test;
import ru.liga.model.entity.Truck;
import ru.liga.model.enums.LoadingMode;
import ru.liga.service.ParcelLoadingService;
import ru.liga.util.TxtParser;
import ru.liga.util.TxtReader;
import ru.liga.validation.FileValidator;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ParcelLoadingServiceTests {

    @Test
    void loadParcelsIntoTrucks_loadByOneByOneMode_createTrucksWithOneParcel() {
        String filePath = "src/test/resources/parcels.txt";
        LoadingMode mode = LoadingMode.ONE_BY_ONE;
        ParcelLoadingService service = new ParcelLoadingService(new TxtParser(new TxtReader(), new FileValidator()));

        List<Truck> trucks = service.loadParcelsIntoTrucks(filePath, mode);

        trucks.forEach(truck -> assertThat(truck.getParcels().size()).isEqualTo(1));
    }

    @Test
    void loadParcelsIntoTrucks_loadByLoadingToCapacityMode_createTrucksWithSeveralRowsAndBottomMustBeGreater() {
        String filePath = "src/test/resources/parcels.txt";
        LoadingMode mode = LoadingMode.LOADING_TO_CAPACITY;
        ParcelLoadingService service = new ParcelLoadingService(new TxtParser(new TxtReader(), new FileValidator()));

        List<Truck> trucks = service.loadParcelsIntoTrucks(filePath, mode);

        trucks.forEach(truck -> {
            HashMap<Integer, String> parcels = truck.getParcels();
            for (int i = 0; i < parcels.size() - 1; i++) {
                assertThat(parcels.get(i).length()).isGreaterThanOrEqualTo(parcels.get(i + 1).length());
            }
        });
    }
}
