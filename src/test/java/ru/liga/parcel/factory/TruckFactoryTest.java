package ru.liga.parcel.factory;

import org.junit.jupiter.api.Test;
import ru.liga.parcel.model.entity.Truck;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class TruckFactoryTest {
    @Test
    void createTrucksOneByOne_EmptyList_ReturnsEmptyList() {
        TruckFactory truckFactory = new TruckFactory();
        List<String> parcels = new ArrayList<>();
        List<Truck> trucks = truckFactory.CreateTrucksOneByOne(parcels);
        assertThat(trucks).isEmpty();
    }

    @Test
    void createTrucksOneByOne_SingleParcel_ReturnsSingleTruck() {
        TruckFactory truckFactory = new TruckFactory();
        List<String> parcels = List.of("111");
        List<Truck> trucks = truckFactory.CreateTrucksOneByOne(parcels);
        assertThat(trucks).hasSize(1);
    }

    @Test
    void createTrucksOneByOne_MultipleParcels_ReturnsMultipleTrucks() {
        TruckFactory truckFactory = new TruckFactory();
        List<String> parcels = List.of("111111", "222222", "333333");
        List<Truck> trucks = truckFactory.CreateTrucksOneByOne(parcels);
        assertThat(trucks).hasSize(3);
    }

    @Test
    void createTrucksLoadingToCapacity_EmptyList_ReturnsEmptyList() {
        TruckFactory truckFactory = new TruckFactory();
        List<String> parcels = new ArrayList<>();
        List<Truck> trucks = truckFactory.CreateTrucksLoadingToCapacity(parcels);
        assertThat(trucks).isEmpty();
    }

    @Test
    void createTrucksLoadingToCapacity_SingleParcel_ReturnsSingleTruck() {
        TruckFactory truckFactory = new TruckFactory();
        List<String> parcels = List.of("111", "111", "222", "222", "333", "333", "444", "444", "555", "555", "666", "666");
        List<Truck> trucks = truckFactory.CreateTrucksLoadingToCapacity(parcels);
        assertThat(trucks).hasSize(1);
    }

    @Test
    void createTrucksLoadingToCapacity_MultipleParcels_ReturnsMultipleTrucks() {
        TruckFactory truckFactory = new TruckFactory();
        List<String> parcels = List.of("111111", "111111", "111111", "111111", "111111", "111111", "111111");
        List<Truck> trucks = truckFactory.CreateTrucksLoadingToCapacity(parcels);
        assertThat(trucks).hasSize(2);
    }
}