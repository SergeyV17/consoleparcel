package ru.liga.parcelmanager.processor.impl.shared;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ParcelRowsGenerator {

    public static final Integer START_INDEX = 0;
    public static final Integer TO_ARRAY_LAST_INDEX = 1;
    public final Integer ZERO_WIDTH = 0;
    public final Integer NEXT_INDEX = 1;

    public List<String> generateRowsCargoByMaxWidth(List<String> cargo, Integer maxWidth) {
        var parcels = new ArrayList<>(cargo);
        parcels.sort(Comparator.comparingInt(String::length).reversed());

        return createRows(maxWidth, parcels);
    }

    private List<String> createRows(Integer maxWidth, List<String> parcels) {
        var rows = new ArrayList<String>();
        for (Integer currentWidth = maxWidth; currentWidth > ZERO_WIDTH; currentWidth--) {
            for (Integer i = START_INDEX; i < parcels.size(); i++) {
                if (parcels.get(i).isEmpty()) {
                    continue;
                }

                if (stockBufferWhenWidthExceed(parcels, i, currentWidth, rows)) {
                    continue;
                }

                StringBuilder rowBuffer = fillRowBuffer(parcels, i, currentWidth);
                stockBufferWhenAllElementsProceed(parcels, rowBuffer, currentWidth, rows, i);
            }
        }

        return rows;
    }

    private boolean stockBufferWhenWidthExceed(
            List<String> parcels,
            Integer i,
            Integer currentWidth,
            List<String> rows) {
        if (parcels.get(i).length() == currentWidth) {
            rows.add(parcels.get(i));
            parcels.set(i, "");
            return true;
        }
        return false;
    }

    private void stockBufferWhenAllElementsProceed(
            List<String> parcels,
            StringBuilder rowBuffer,
            Integer currentWidth,
            List<String> rows,
            Integer i) {
        if (rowBuffer.length() <= currentWidth) {
            rows.add(rowBuffer.toString());
            parcels.set(i, "");
        }
    }

    private StringBuilder fillRowBuffer(List<String> parcels, Integer i, Integer currentWidth) {
        StringBuilder rowBuffer = new StringBuilder(parcels.get(i));
        for (int j = i + NEXT_INDEX; j < parcels.size(); j++) {
            var right = parcels.get(j);
            if (right.isEmpty()) {
                continue;
            }

            if (stockBuffer(parcels, i, currentWidth, rowBuffer, right, j)) {
                continue;
            }

            if (stockBufferWhenWithExceed(parcels, i, currentWidth, rowBuffer, right, j)) {
                break;
            }
        }
        return rowBuffer;
    }

    private boolean stockBuffer(
            List<String> parcels,
            Integer i,
            Integer currentWidth,
            StringBuilder rowBuffer,
            String right,
            Integer j) {
        if (rowBuffer.length() + right.length() < currentWidth) {
            rowBuffer.append(right);
            parcels.set(i, "");
            parcels.set(j, "");
            return true;
        }
        return false;
    }

    private boolean stockBufferWhenWithExceed(
            List<String> parcels,
            Integer i,
            Integer currentWidth,
            StringBuilder rowBuffer,
            String right,
            Integer j) {
        if (rowBuffer.length() + right.length() == currentWidth ||
                j == parcels.size() - TO_ARRAY_LAST_INDEX) {
            rowBuffer.append(right);
            parcels.set(i, "");
            parcels.set(j, "");
            return true;
        }
        return false;
    }
}
