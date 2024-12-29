package ru.liga.parcelmanager.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.liga.parcelmanager.model.enums.OutputType;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Output<T> {

    private final String filePath;
    private final OutputType outputType;
    private final List<T> values;

}
