package ru.liga.parcelmanager.model.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Parcel {

    private String name;

    private String form;

    private String symbol;
}
