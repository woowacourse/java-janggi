package utils;

import java.util.List;

public class Parser {

    private static final String DELIMITER = ",";
    private static final String STRICT_NUMERIC_PATTERN = "[0-9]\\d*";

    private static final String INPUT_EMPTY = "[ERROR] 입력값이 비어있습니다.";
    private static final String NOT_STRICT_NUMERIC = "[ERROR] 입력값은 숫자여야 합니다.(0, 음수, 공백 X)";
    private static final String INTEGER_OVERFLOW = "[ERROR] 입력값이 정수의 최댓값을 초과했습니다.";


    public static List<Integer> parseDelimitedToIntegersStrict(String input) {
        return splitByDelimiterStrict(input).stream()
                .map(Parser::parseIntStrict)
                .toList();
    }

    private static List<String> splitByDelimiterStrict(String input) {
        validateBlank(input);
        return List.of(input.split(DELIMITER));
    }

    public static int parseIntStrict(String input) {
        validateBlank(input);
        validateNumericStrict(input);
        validateIntegerRange(input);

        return Integer.parseInt(input);
    }

    private static void validateBlank(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(INPUT_EMPTY);
        }
    }

    private static void validateNumericStrict(String input) {
        if (!input.matches(STRICT_NUMERIC_PATTERN)) {
            throw new IllegalArgumentException(NOT_STRICT_NUMERIC);
        }
    }

    private static void validateIntegerRange(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INTEGER_OVERFLOW);
        }
    }
}
