package ru.liga.parcelmanager.processor.impl;

import org.junit.jupiter.api.Test;
import ru.liga.parcelmanager.factory.TruckFactory;
import ru.liga.parcelmanager.model.entity.Truck;
import ru.liga.parcelmanager.processor.impl.shared.ParcelRowsGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FullCapacityLoadingProcessorTest {

    @Test
    public void testLoadParcelsIntoTrucks_EmptyList_ReturnsEmptyList() {
        FullCapacityLoadingProcessor processor = new FullCapacityLoadingProcessor(new TruckFactory(), new ParcelRowsGenerator());
        List<String> cargo = new ArrayList<>();
        List<Truck> trucks = processor.loadParcelsIntoTrucks(cargo, null);
        assertThat(trucks).isEmpty();
    }

    @Test
    public void testLoadParcelsIntoTrucks_SingleParcel_ReturnsSingleTruck() {
        FullCapacityLoadingProcessor processor = new FullCapacityLoadingProcessor(new TruckFactory(), new ParcelRowsGenerator());
        List<String> parcel = List.of("111111");
        List<Truck> trucks = processor.loadParcelsIntoTrucks(parcel, null);
        assertThat(trucks).hasSize(1);
    }

    @Test
    public void testLoadParcelsIntoTrucks_MultipleParcels_ReturnsMultipleTrucks() {
        FullCapacityLoadingProcessor processor = new FullCapacityLoadingProcessor(new TruckFactory(), new ParcelRowsGenerator());
        List<String> cargo = List.of("111111", "222222", "333333", "444444", "555555", "666666", "777777");
        List<Truck> trucks = processor.loadParcelsIntoTrucks(cargo, null);

        assertThat(trucks).hasSize(2);
        assertThat(trucks.get(0).getCargo().getParcels())
                .containsExactly("111111", "222222", "333333", "444444", "555555", "666666");
        assertThat(trucks.get(1).getCargo().getParcels()).containsExactly("777777");
    }

    @Test
    public void testLoadParcelsIntoTrucks_ParcelsThatCanFitInOneTruck_ReturnsOneTruck() {
        FullCapacityLoadingProcessor processor = new FullCapacityLoadingProcessor(new TruckFactory(), new ParcelRowsGenerator());
        List<String> cargo = List.of("111111", "222222", "3333");
        List<Truck> trucks = processor.loadParcelsIntoTrucks(cargo, null);

        assertThat(trucks).hasSize(1);
        assertThat(trucks.getFirst().getCargo().getParcels()).containsExactly("111111", "222222", "3333");
    }
}