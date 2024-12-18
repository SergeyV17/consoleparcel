package ru.liga.parcel.model.entity;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class Truck {
    public static final int MAX_HEIGHT = 6;
    public static final int MAX_WIDTH = 6;

    private final HashMap<Integer, String> parcels;

    public Truck(String parcel) {
        if (parcel.length() > MAX_WIDTH) {
            throw new IllegalArgumentException(
                    String.format("Parcel %s has invalid width. Max available width: %s", parcel, MAX_WIDTH));
        }
        this.parcels = new HashMap<>();
        this.parcels.put(0, parcel);
    }

    public Truck(HashMap<Integer, String> parcels) {
        this.parcels = parcels;
    }

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder();
        for (int i = MAX_HEIGHT - 1; i >= 0; i--) {
            stringBuilder.append("+");
            stringBuilder.append(parcels.getOrDefault(i, ""));
            stringBuilder.append(" ".repeat(MAX_WIDTH - parcels.getOrDefault(i, "").length()));
            stringBuilder.append("+\n");
        }
        stringBuilder.append("++++++++\n\n");

        return stringBuilder.toString();
    }
}
