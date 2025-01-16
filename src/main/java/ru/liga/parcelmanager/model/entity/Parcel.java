package ru.liga.parcelmanager.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class Parcel {

    public Parcel(String name, String form, String symbol) {
        this.name = name;
        this.form = form;
        this.symbol = symbol;
    }

    private final String name;

    @Setter
    public String form;

    @Setter
    public String symbol;
}
