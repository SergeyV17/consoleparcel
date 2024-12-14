package ru.liga.service;

import ru.liga.entity.Truck;

import java.util.List;

public class PrintingService {
    public void PrintTrucks(List<Truck> trucks) {

        // TODO печать всех грузовиков в одну строку, но сначала надо разобраться с алгоритмом
        for (Truck truck : trucks) {
            String string = truck.toString();
            System.out.print(string);
        }
    }
}
