package ru.liga.parcelmanager.model.entity;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Truck {

    public static final int MAX_HEIGHT = 6;
    public static final int MAX_WIDTH = 6;

    private static final int TO_ARRAY_LAST_INDEX = 1;
    private static final int ARRAY_START_INDEX = 0;

    private final int height = MAX_HEIGHT;
    private final int width = MAX_WIDTH;

    private ArrayList<Parcel> parcels;

    public Truck() {
        parcels = new ArrayList<>();
    }

    public void loadTruck(ArrayList<Parcel> newParcels) {

        // TODO Sergey Vlasenko здесь работать не будет
        if (newParcels.size() > MAX_HEIGHT) {
            throw new IllegalArgumentException("Truck cannot load more than " + MAX_HEIGHT);
        }
        parcels = newParcels;
    }

    @Override
    public String toString() {
        if (parcels.isEmpty()) {
            return "";
        }

        var stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        for (int i = MAX_HEIGHT - TO_ARRAY_LAST_INDEX; i >= ARRAY_START_INDEX; i--) {
            String currentElement = parcels.size() > i ? parcels.get(i) : "";

            stringBuilder.append("+");
            stringBuilder.append(currentElement);
            stringBuilder.append(" ".repeat(MAX_WIDTH - currentElement.length()));
            stringBuilder.append("+\n");
        }
        stringBuilder.append("++++++++\n\n");

        return stringBuilder.toString();
    }
}
