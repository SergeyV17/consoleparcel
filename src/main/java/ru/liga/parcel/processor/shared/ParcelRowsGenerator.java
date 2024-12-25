package ru.liga.parcel.processor.shared;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ParcelRowsGenerator {
    public List<String> GenerateRowsCargoByMaxWidth(List<String> cargo, Integer maxWidth) {
        var parcels = new ArrayList<>(cargo);
        parcels.sort(Comparator.comparingInt(String::length).reversed());

        var rows = new ArrayList<String>();
        for (int currentWidth = maxWidth; currentWidth > 0; currentWidth--) {
            for (int i = 0; i < parcels.size(); i++) {
                if (parcels.get(i).isEmpty()) {
                    continue;
                }

                if (parcels.get(i).length() == currentWidth) {
                    rows.add(parcels.get(i));
                    parcels.set(i, "");
                    continue;
                }

                StringBuilder rowBuffer = new StringBuilder(parcels.get(i));
                for (int j = i + 1; j < parcels.size(); j++) {

                    var right = parcels.get(j);

                    if (rowBuffer.length() + right.length() < currentWidth) {
                        rowBuffer.append(right);
                        parcels.set(i, "");
                        parcels.set(j, "");
                        continue;
                    }

                    if (rowBuffer.length() + right.length() == currentWidth || j == parcels.size() - 1) {
                        rowBuffer.append(right);
                        parcels.set(i, "");
                        parcels.set(j, "");
                        break;
                    }
                }

                if (rowBuffer.length() == currentWidth || i == parcels.size() - 1) {
                    rows.add(rowBuffer.toString());
                }
            }
        }

        return rows;
    }
}
