package ru.liga.parcelmanager.processor.impl;

import org.junit.jupiter.api.Test;
import ru.liga.parcelmanager.factory.TruckFactory;
import ru.liga.parcelmanager.model.entity.Truck;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OneByOneLoadingProcessorTest {

    @Test
    public void testLoadParcelsIntoTrucks_EmptyList_ReturnsEmptyList() {
        OneByOneLoadingProcessor processor = new OneByOneLoadingProcessor(new TruckFactory());
        List<String> cargo = new ArrayList<>();
        List<Truck> trucks = processor.loadParcelsIntoTrucks(cargo, null);

        assertThat(trucks).isEmpty();
    }

    @Test
    public void testLoadParcelsIntoTrucks_SingleParcel_ReturnsSingleTruck() {
        OneByOneLoadingProcessor processor = new OneByOneLoadingProcessor(new TruckFactory());
        List<String> cargo = List.of("111111");
        List<Truck> trucks = processor.loadParcelsIntoTrucks(cargo, null);

        assertThat(trucks).hasSize(1);
        assertThat(trucks.getFirst().getCargo().getParcels()).containsExactly("111111");
    }

    @Test
    public void testLoadParcelsIntoTrucks_MultipleParcels_ReturnsMultipleTrucks() {
        OneByOneLoadingProcessor processor = new OneByOneLoadingProcessor(new TruckFactory());
        List<String> parcel = List.of("111111", "222222", "333333");
        List<Truck> trucks = processor.loadParcelsIntoTrucks(parcel, null);

        assertThat(trucks).hasSize(3);
        assertThat(trucks.get(0).getCargo().getParcels()).containsExactly("111111");
        assertThat(trucks.get(1).getCargo().getParcels()).containsExactly("222222");
        assertThat(trucks.get(2).getCargo().getParcels()).containsExactly("333333");
    }
}