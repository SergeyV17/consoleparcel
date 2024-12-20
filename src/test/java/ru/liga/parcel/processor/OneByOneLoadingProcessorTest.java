package ru.liga.parcel.processor;

import org.junit.jupiter.api.Test;
import ru.liga.parcel.factory.TruckFactory;
import ru.liga.parcel.model.entity.Truck;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OneByOneLoadingProcessorTest {
    @Test
    public void testLoadCargoIntoTrucks_EmptyList_ReturnsEmptyList() {
        OneByOneLoadingProcessor processor = new OneByOneLoadingProcessor(new TruckFactory());
        List<String> cargo = new ArrayList<>();
        List<Truck> trucks = processor.loadCargoIntoTrucks(cargo);

        assertThat(trucks).isEmpty();
    }

    @Test
    public void testLoadCargoIntoTrucks_SingleParcel_ReturnsSingleTruck() {
        OneByOneLoadingProcessor processor = new OneByOneLoadingProcessor(new TruckFactory());
        List<String> cargo = List.of("111111");
        List<Truck> trucks = processor.loadCargoIntoTrucks(cargo);

        assertThat(trucks).hasSize(1);
        assertThat(trucks.getFirst().getCargo().getElements()).containsExactly("111111");
    }

    @Test
    public void testLoadCargoIntoTrucks_MultipleParcels_ReturnsMultipleTrucks() {
        OneByOneLoadingProcessor processor = new OneByOneLoadingProcessor(new TruckFactory());
        List<String> cargo = List.of("111111", "222222", "333333");
        List<Truck> trucks = processor.loadCargoIntoTrucks(cargo);

        assertThat(trucks).hasSize(3);
        assertThat(trucks.get(0).getCargo().getElements()).containsExactly("111111");
        assertThat(trucks.get(1).getCargo().getElements()).containsExactly("222222");
        assertThat(trucks.get(2).getCargo().getElements()).containsExactly("333333");
    }
}