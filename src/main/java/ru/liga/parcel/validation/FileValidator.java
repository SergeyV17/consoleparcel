package ru.liga.parcel.validation;

import lombok.extern.slf4j.Slf4j;
import ru.liga.parcel.model.entity.Truck;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
public class FileValidator {
    public static final String DELIMITER = "\r\n";
    private final Pattern FILE_LINE_PATTERN = Pattern.compile("^(\\d)\\1*$");

    public void validate(List<String> parcel) {
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
            var errorsString = String.join(DELIMITER, errors);
            throw new IllegalArgumentException(errorsString);
        }
    }
}
