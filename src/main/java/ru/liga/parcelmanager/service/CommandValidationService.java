package ru.liga.parcelmanager.service;

import ru.liga.parcelmanager.exceptions.InvalidJsonException;
import ru.liga.parcelmanager.exceptions.InvalidTxtException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandValidationService {

    private static final Integer FIRST_MATCHER_GROUP_NUMBER = 1;
    private static final Pattern TXT_FILE_PATTERN = Pattern.compile("(.+\\.txt)");
    private static final Pattern JSON_FILE_PATTERN = Pattern.compile("(.+\\.json)");

    public String validateLoadTruckCommandAndGetFilePath(String command) {
        Matcher matcher = TXT_FILE_PATTERN.matcher(command);
        checkTxtFileName(matcher, command);

        return matcher.group(FIRST_MATCHER_GROUP_NUMBER);
    }

    public String validateUnloadTruckCommandAndGetFilePath(String command) {
        Matcher matcher = JSON_FILE_PATTERN.matcher(command);
        checkJsonFileName(matcher, command);

        return matcher.group(FIRST_MATCHER_GROUP_NUMBER);
    }

    private void checkJsonFileName(Matcher matcher, String command) {
        if (!matcher.matches()) {
            throw new InvalidJsonException(command);
        }
    }

    private void checkTxtFileName(Matcher matcher, String command) {
        if (!matcher.matches()) {
            throw new InvalidTxtException(command);
        }
    }
}
