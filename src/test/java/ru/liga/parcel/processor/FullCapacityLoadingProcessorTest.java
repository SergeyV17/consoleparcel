package ru.liga.parcel.processor;

import org.junit.jupiter.api.Test;
import ru.liga.parcel.factory.TruckFactory;
import ru.liga.parcel.model.entity.Truck;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FullCapacityLoadingProcessorTest {
    @Test
    public void testLoadCargoIntoTrucks_EmptyList_ReturnsEmptyList() {
        FullCapacityLoadingProcessor processor = new FullCapacityLoadingProcessor(new TruckFactory());
        List<String> cargo = new ArrayList<>();
        List<Truck> trucks = processor.loadCargoIntoTrucks(cargo);
        assertThat(trucks).isEmpty();
    }

    @Test
    public void testLoadCargoIntoTrucks_SingleParcel_ReturnsSingleTruck() {
        FullCapacityLoadingProcessor processor = new FullCapacityLoadingProcessor(new TruckFactory());
        List<String> cargo = List.of("111111");
        List<Truck> trucks = processor.loadCargoIntoTrucks(cargo);
        assertThat(trucks).hasSize(1);
    }

    @Test
    public void testLoadCargoIntoTrucks_MultipleParcels_ReturnsMultipleTrucks() {
        FullCapacityLoadingProcessor processor = new FullCapacityLoadingProcessor(new TruckFactory());
        List<String> cargo = List.of("111111", "222222", "333333", "444444", "555555", "666666", "777777");
        List<Truck> trucks = processor.loadCargoIntoTrucks(cargo);

        assertThat(trucks).hasSize(2);
        assertThat(trucks.get(0).getCargo().getElements())
                .containsExactly("111111", "222222", "333333", "444444", "555555", "666666");
        assertThat(trucks.get(1).getCargo().getElements()).containsExactly("777777");
    }

    @Test
    public void testLoadCargoIntoTrucks_ParcelsThatCanFitInOneTruck_ReturnsOneTruck() {
        FullCapacityLoadingProcessor processor = new FullCapacityLoadingProcessor(new TruckFactory());
        List<String> cargo = List.of("111111", "222222", "3333");
        List<Truck> trucks = processor.loadCargoIntoTrucks(cargo);

        assertThat(trucks).hasSize(1);
        assertThat(trucks.getFirst().getCargo().getElements()).containsExactly("111111", "222222", "3333");
    }
}