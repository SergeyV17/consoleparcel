package ru.liga.parcelmanager.processor;

import ru.liga.parcelmanager.model.Output;

public interface OutputProcessor {

    void write(Output<?> output);
}
