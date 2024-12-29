package ru.liga.parcelmanager.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Cargo {

    private final List<String> parcels;

    @SuppressWarnings("unused")
    public Cargo() {
        parcels = new ArrayList<>();
    }
}
