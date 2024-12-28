package ru.liga.parcelmanager.factory;

import ru.liga.parcelmanager.model.entity.Truck;

public class TruckFactory {

    public Truck createTruck() {
        return new Truck();
    }
}
