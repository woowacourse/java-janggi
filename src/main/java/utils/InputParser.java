package utils;

import java.util.List;

public class InputParser {

    private static final String DELIMITER = ",";

    private InputParser() {}

    public static List<String> split(String userInput) {
        return List.of(userInput.split(DELIMITER));
    }
}
