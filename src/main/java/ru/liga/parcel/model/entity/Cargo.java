package ru.liga.parcel.model.entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Cargo {
    private final List<String> parcels;

    @SuppressWarnings("unused")
    public Cargo() {
        this.parcels = new ArrayList<>();
    }

    public Cargo(List<String> elements) {
        this.parcels = elements;
    }
}
