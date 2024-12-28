package ru.liga.parcelmanager.service;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
public class FileValidationService {

    public static final String DELIMITER = "\r\n";
    private final Pattern FILE_LINE_PATTERN = Pattern.compile("^(\\d)\\1*$");

    public void validate(List<String> parcelStrings) {
        if (parcelStrings.isEmpty()) {
            throw new IllegalArgumentException("File lines cannot be empty.");
        }

        List<String> errors = new ArrayList<>();
        parcelStrings.forEach(parcel -> checkParcelForErrors(parcel, errors));

        if (!errors.isEmpty()) {
            var errorsString = String.join(DELIMITER, errors);
            throw new IllegalArgumentException(errorsString);
        }
    }

    private void checkParcelForErrors(String parcel, List<String> errors) {
        if (!FILE_LINE_PATTERN.matcher(parcel).matches()) {
            errors.add("File parcel has invalid format: " + parcel);
        }
    }
}
