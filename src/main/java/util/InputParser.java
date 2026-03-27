package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static util.ErrorMessage.*;

public class InputParser {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\d+$");
    private static final String DELIMITER = ",";
    private static final int REQUIRED_TOKEN_COUNT = 2;

    public static List<Integer> parse(String input) {
        validateNotBlank(input);

        List<String> tokens = splitAndTrim(input);

        validateTokenCount(tokens);
        validateNoBlankToken(tokens);
        validateNumberFormat(tokens);

        return convertToIntegers(tokens);
    }

    private static void validateNotBlank(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(ERROR_BLANK_INPUT.getMessage());
        }
    }

    private static List<String> splitAndTrim(String input) {
        return Arrays.stream(input.split(DELIMITER))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private static void validateTokenCount(List<String> tokens) {
        if (tokens.size() != REQUIRED_TOKEN_COUNT) {
            throw new IllegalArgumentException(String.format(ERROR_TOKEN_COUNT.getMessage(), tokens.size()));
        }
    }

    private static void validateNoBlankToken(List<String> tokens) {
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).isBlank()) {
                throw new IllegalArgumentException(String.format(ERROR_BLANK_TOKEN.getMessage(), i + 1));
            }
        }
    }

    private static void validateNumberFormat(List<String> tokens) {
        for (String token : tokens) {
            if (!NUMBER_PATTERN.matcher(token).matches()) {
                throw new IllegalArgumentException(String.format(ERROR_NUMBER_FORMAT.getMessage(), token));
            }
        }
    }

    private static List<Integer> convertToIntegers(List<String> tokens) {
        List<Integer> numbers = new ArrayList<>();
        for (String token : tokens) {
            numbers.add(Integer.parseInt(token));
        }
        return numbers;
    }
}
