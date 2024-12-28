package ru.liga.parcelmanager.processor.impl;

import lombok.extern.slf4j.Slf4j;
import ru.liga.parcelmanager.model.Output;
import ru.liga.parcelmanager.processor.OutputProcessor;

@Slf4j
public class ConsoleOutputProcessor implements OutputProcessor {

    @Override
    public void write(Output<?> output) {
        for (String value : output.getValues().stream().map(Object::toString).toList()) {
            log.info(value);
        }
    }
}
