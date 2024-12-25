package ru.liga.parcel.model.entity;

import lombok.Getter;

import java.util.List;

@Getter
public class Cargo {
    private final List<String> parcels;

    public Cargo(List<String> elements) {
        this.parcels = elements;
    }
}
