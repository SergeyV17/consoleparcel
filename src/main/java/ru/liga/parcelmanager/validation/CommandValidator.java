package ru.liga.parcelmanager.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandValidator {

    public static final int FIRST_MATCHER_GROUP_NUMBER = 1;
    private final Pattern TXT_FILE_PATTERN = Pattern.compile("(.+\\.txt)");
    private final Pattern JSON_FILE_PATTERN = Pattern.compile("(.+\\.json)");

    public String validateLoadTruckCommandAndGetFilePath(String command) {
        Matcher matcher = TXT_FILE_PATTERN.matcher(command);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid txt file: " + command);
        }

        return matcher.group(FIRST_MATCHER_GROUP_NUMBER);
    }

    public String validateUnloadTruckCommandAndGetFilePath(String command) {
        Matcher matcher = JSON_FILE_PATTERN.matcher(command);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid json file: " + command);
        }

        return matcher.group(FIRST_MATCHER_GROUP_NUMBER);
    }
}
