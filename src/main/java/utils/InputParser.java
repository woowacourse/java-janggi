package utils;

import java.util.List;
import java.util.stream.Collectors;

public class InputParser {

    private static final String DELIMITER = ",";

    private InputParser() {}

    public static List<Integer> splitAndConvert(String userInput) {
        List<String> splitResult = List.of(userInput.split(DELIMITER));
        return splitResult.stream()
            .map(Integer::parseInt)
            .toList();
    }
}
