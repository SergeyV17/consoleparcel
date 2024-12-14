package ru.liga.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.model.enums.LoadingMode;
import ru.liga.util.TxtParser;

@Slf4j
@RequiredArgsConstructor
public class ParcelLoadingService {

    private final TxtParser txtParser;

    public String loadParcelsIntoTrucks(String filePath, LoadingMode mode) {
        var cargo = txtParser.parseCargoFromFile(filePath);
        if (cargo.isEmpty()) {
            log.error("Не обнаружено валидного груза в файле {}", filePath);
            return "";
        }

        if (mode == LoadingMode.LOADING_TO_CAPACITY) {
            // TODO реализовать
        }
        else if (mode == LoadingMode.ONE_BY_ONE) {
            // TODO реализовать
        }

        return "true";
    }
}
