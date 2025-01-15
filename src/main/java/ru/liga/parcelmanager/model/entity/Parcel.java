package ru.liga.parcelmanager.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class Parcel {

    private final String name;

    @Setter
    private String form;

    @Setter
    private String symbol;

}
