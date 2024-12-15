package ru.liga.validation;

import ru.liga.model.entity.Truck;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FileValidator {
    private final Pattern FILE_LINE_PATTERN = Pattern.compile("^(\\d)\\1*$");

    public void validateFileLines(List<String> parcel) {
        if (parcel.isEmpty()) {
            throw new IllegalArgumentException("File lines cannot be empty.");
        }

        List<String> errors = new ArrayList<>();
        parcel.forEach(line -> {
            if (!FILE_LINE_PATTERN.matcher(line).matches()) {
                errors.add("File line has invalid format: " + line);
            }

            if (line.length() > Truck.MAX_WIDTH) {
                errors.add(String.format("Parcel %s has invalid width. Max available width: %s", line, Truck.MAX_WIDTH));
            }
        });

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join("\n", errors));
        }
    }
}
