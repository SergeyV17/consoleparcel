package ru.liga.parcelmanager.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandValidationService {

    private static final Integer FIRST_MATCHER_GROUP_NUMBER = 1;
    private static final Pattern TXT_FILE_PATTERN = Pattern.compile("(.+\\.txt)");
    private static final Pattern JSON_FILE_PATTERN = Pattern.compile("(.+\\.json)");

    public String validateLoadTruckCommandAndGetFilePath(String command) {
        Matcher matcher = TXT_FILE_PATTERN.matcher(command);
        checkFileNameAndThrow(matcher, "Invalid txt file: ", command);

        return matcher.group(FIRST_MATCHER_GROUP_NUMBER);
    }

    public String validateUnloadTruckCommandAndGetFilePath(String command) {
        Matcher matcher = JSON_FILE_PATTERN.matcher(command);
        checkFileNameAndThrow(matcher, "Invalid json file: ", command);

        return matcher.group(FIRST_MATCHER_GROUP_NUMBER);
    }

    private void checkFileNameAndThrow(Matcher matcher, String x, String command) {
        if (!matcher.matches()) {
            throw new IllegalArgumentException(x + command);
        }
    }
}
