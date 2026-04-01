package utils;

import java.util.List;
import message.ErrorMessage;
import message.ParserErrorMessage;

public class InputParser {

    private static final String DELIMITER = ",";
    private static final String STRICT_NUMERIC_PATTERN = "[0-9]\\d*";


    public static List<Integer> parseDelimitedToIntegersStrict(String input) {
        return splitByDelimiterStrict(input).stream()
                .map(InputParser::parseIntStrict)
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
            throw new IllegalArgumentException(ParserErrorMessage.INPUT_EMPTY.getMessage());
        }
    }

    private static void validateNumericStrict(String input) {
        if (!input.matches(STRICT_NUMERIC_PATTERN)) {
            throw new IllegalArgumentException(ParserErrorMessage.NOT_STRICT_NUMERIC.getMessage());
        }
    }

    private static void validateIntegerRange(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ParserErrorMessage.INTEGER_OVERFLOW.getMessage());
        }
    }
}
