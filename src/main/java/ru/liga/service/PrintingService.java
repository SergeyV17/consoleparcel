package ru.liga.service;

import ru.liga.model.entity.Truck;

import java.util.List;

public class PrintingService {
    public void PrintTrucks(List<Truck> trucks) {
        for (Truck truck : trucks) {
            String string = truck.toString();
            System.out.print(string);
        }
    }
}
