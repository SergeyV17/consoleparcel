package ru.liga.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.entity.Truck;
import ru.liga.model.enums.LoadingMode;
import ru.liga.util.TxtParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
public class ParcelLoadingService {
    private final TxtParser txtParser;

    public List<Truck> loadParcelsIntoTrucks(String filePath, LoadingMode mode) {
        var parcels = txtParser.parseCargoFromFile(filePath);
        if (parcels.isEmpty()) {
            log.error("Parcels not found in {}", filePath);
            return Collections.emptyList();
        }

        if (mode == LoadingMode.LOADING_TO_CAPACITY) {
            // результирующий список всех кузовов слитно
            var concatenatedCarcases = new ArrayList<String>();

            // Идем от максимальной ширины кузова к минимальной
            for (int currentWidth = Truck.MAX_WIDTH; currentWidth > 0 ; currentWidth--) {
                // Фиксируем первый элемент и складываем с ним все последующие от него
                for (int i = 0; i < parcels.size(); i++) {

                    var rowBuffer = parcels.get(i);

                    // если левый элемент сразу равен текущей ширине, то добавляем его и переходим к следующему
                    if (rowBuffer.length() == currentWidth) {
                        concatenatedCarcases.add(parcels.get(i));
                        parcels.set(i, "");
                        continue;
                    }

                    // собираем сумму всех правых элементов
                    for (int j = i + 1; j < parcels.size(); j++) {

                        var right = parcels.get(j);

                        // Если сумма длин левого и правого элементов равна текущей ширине,
                        // то добавляем их в карту и удаляем из списка
                        if (rowBuffer.length() + right.length() == currentWidth) {
                            rowBuffer += right;
                            parcels.set(i, "");
                            parcels.set(j, "");
                            break;
                        }
                    }

                    if (rowBuffer.length() == currentWidth) {
                        concatenatedCarcases.add(rowBuffer);
                    }
                }
            }

            var batches = tileBatches(concatenatedCarcases, Truck.MAX_HEIGHT)
                    .stream()
                    .map(batch -> {
                        var map = new HashMap<Integer, String>();
                        for (int i = batch.size() - 1; i > 0; i--) {
                            map.put(i, batch.get(i));
                        }
                        return map;
                    })
                    .map(Truck::new)
                    .toList();

            return batches;
        }
        else if (mode == LoadingMode.ONE_BY_ONE) {
            return parcels.stream().map(Truck::new).toList();
        }

        return Collections.emptyList();
    }

    public static List<List<String>> tileBatches(List<String> items, int batchSize) {
        return IntStream.range(0, items.size() / batchSize + 1)
                .mapToObj(i -> items.subList(i * batchSize, Math.min((i + 1) * batchSize, items.size())))
                .collect(Collectors.toList());
    }
}
