package ru.liga.parcel.model.entity;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Truck {
    public static final int MAX_HEIGHT = 6;
    public static final int MAX_WIDTH = 6;

    private Cargo cargo;

    public Truck() {
        this.cargo = new Cargo(new ArrayList<>());
    }

    public void loadTruck(Cargo cargo) {
        if (cargo.getElements().size() > MAX_HEIGHT) {
            throw new IllegalArgumentException("Truck cannot load more than " + MAX_HEIGHT);
        }
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        if (cargo.getElements().isEmpty()) {
            return "";
        }

        var stringBuilder = new StringBuilder();
        for (int i = Truck.MAX_HEIGHT - 1; i >= 0; i--) {
            String currentElement = cargo.getElements().size() > i ? cargo.getElements().get(i) : "";

            stringBuilder.append("+");
            stringBuilder.append(currentElement);
            stringBuilder.append(" ".repeat(MAX_WIDTH - currentElement.length()));
            stringBuilder.append("+\n");
        }
        stringBuilder.append("++++++++\n\n");

        return stringBuilder.toString();
    }
}
