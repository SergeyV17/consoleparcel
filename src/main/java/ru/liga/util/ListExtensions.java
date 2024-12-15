package ru.liga.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListExtensions {
    public static <T> List<List<T>> tile(List<T> source, int batchSize) {
        if (source == null || batchSize <= 0) {
            throw new IllegalArgumentException("Source cannot be null and batch size must be greater than 0.");
        }

        return new ArrayList<>(IntStream.range(0, source.size())
                .boxed()
                .collect(Collectors.groupingBy(
                        index -> index / batchSize,
                        LinkedHashMap::new,
                        Collectors.mapping(source::get, Collectors.toList())
                ))
                .values());
    }
}
