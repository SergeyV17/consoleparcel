package ru.liga.parcelmanager.processor.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.liga.parcelmanager.factory.TruckFactory;
import ru.liga.parcelmanager.model.entity.Truck;
import ru.liga.parcelmanager.processor.impl.shared.NumberOfTrucksCalculator;
import ru.liga.parcelmanager.processor.impl.shared.ParcelRowsGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UniformLoadingProcessorTest {

    @Test
    public void loadParcelsIntoTrucks_NoNumberOfTrucksProvided_ReturnsNonEmptyList() {

        UniformLoadingProcessor uniformLoadingProcessor = new UniformLoadingProcessor(
                Mockito.mock(TruckFactory.class),
                Mockito.mock(ParcelRowsGenerator.class),
                Mockito.mock(NumberOfTrucksCalculator.class));

        List<String> cargo = new ArrayList<>();
        cargo.add("111111");
        cargo.add("222222");
        cargo.add("333333");

        List<Truck> trucks = uniformLoadingProcessor.loadParcelsIntoTrucks(cargo, null);

        assertThat(trucks).isNotEmpty();
    }

    @Test
    public void loadParcelsIntoTrucks_WithNumberOfTrucksProvided_ReturnsListOfSizeNumberOfTrucks() {
        UniformLoadingProcessor uniformLoadingProcessor = new UniformLoadingProcessor(
                Mockito.mock(TruckFactory.class),
                Mockito.mock(ParcelRowsGenerator.class),
                Mockito.mock(NumberOfTrucksCalculator.class));

        List<String> cargo = new ArrayList<>();
        cargo.add("111111");
        cargo.add("222222");
        cargo.add("333333");
        int numberOfTrucks = 2;

        List<Truck> trucks = uniformLoadingProcessor.loadParcelsIntoTrucks(cargo, numberOfTrucks);

        assertThat(trucks).hasSize(numberOfTrucks);
    }

    @Test
    public void calculateNumberOfTrucks_ReturnsPositiveNumber() {
        List<String> cargo = new ArrayList<>();
        cargo.add("111111");
        cargo.add("222222");
        cargo.add("333333");

        int numberOfTrucks = new NumberOfTrucksCalculator().calculateNumberOfTrucks(cargo);

        assertThat(numberOfTrucks).isGreaterThan(0);
    }

    @Test
    public void distributeParcelsByTruck_ReturnsMapOfSizeNumberOfTrucks() {
        List<String> rows = new ArrayList<>();
        rows.add("111333");
        rows.add("222444");
        rows.add("333555");
        int numberOfTrucks = 2;

        Map<Integer, List<String>> parcelsByTruck = new UniformLoadingProcessor(
                new TruckFactory(),
                Mockito.mock(ParcelRowsGenerator.class),
                new NumberOfTrucksCalculator())
                .distributeParcelsByTruck(rows, numberOfTrucks);

        assertThat(parcelsByTruck).hasSize(numberOfTrucks);
    }

    @Test
    public void createTrucksByConcatenatedStringCarcases_ReturnsListOfSizeOne() {
        TruckFactory truckFactory = Mockito.mock(TruckFactory.class);
        ParcelRowsGenerator rowsGenerator = Mockito.mock(ParcelRowsGenerator.class);
        NumberOfTrucksCalculator numberOfTrucksCalculator = Mockito.mock(NumberOfTrucksCalculator.class);
        UniformLoadingProcessor uniformLoadingProcessor = new UniformLoadingProcessor(
                truckFactory,
                rowsGenerator,
                numberOfTrucksCalculator);

        Map<Integer, List<String>> concatenatedCarcases = new HashMap<>();
        concatenatedCarcases.put(0, new ArrayList<>());
        concatenatedCarcases.get(0).add("111111");
        concatenatedCarcases.get(0).add("222222");

        List<Truck> trucks = uniformLoadingProcessor.createTrucksByConcatenatedStringCarcases(concatenatedCarcases);

        assertThat(trucks).hasSize(1);
    }

    @Test
    public void createTrucksByConcatenatedStringCarcases_MultipleTrucks_ReturnsListOfSizeTwo() {
        TruckFactory truckFactory = Mockito.mock(TruckFactory.class);
        ParcelRowsGenerator rowsGenerator = Mockito.mock(ParcelRowsGenerator.class);
        NumberOfTrucksCalculator numberOfTrucksCalculator = Mockito.mock(NumberOfTrucksCalculator.class);
        UniformLoadingProcessor uniformLoadingProcessor = new UniformLoadingProcessor(
                truckFactory,
                rowsGenerator,
                numberOfTrucksCalculator);

        Map<Integer, List<String>> concatenatedCarcases = new HashMap<>();
        concatenatedCarcases.put(0, new ArrayList<>());
        concatenatedCarcases.get(0).add("111111");
        concatenatedCarcases.get(0).add("222222");
        concatenatedCarcases.put(1, new ArrayList<>());
        concatenatedCarcases.get(1).add("333333");
        concatenatedCarcases.get(1).add("444444");

        List<Truck> trucks = uniformLoadingProcessor.createTrucksByConcatenatedStringCarcases(concatenatedCarcases);

        assertThat(trucks).hasSize(2);
    }
}