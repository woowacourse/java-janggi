package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InputParser {

    private static final String ERROR_BLANK_INPUT = "입력값이 비어 있습니다. '열,행' 형식으로 입력하세요. (예: 5,1)";
    private static final String ERROR_TOKEN_COUNT = "입력값은 쉼표(,)로 구분된 2개여야 합니다. (입력된 값 %d개) (예: 5,1)";
    private static final String ERROR_BLANK_TOKEN = "%d번째 값이 비어 있습니다. '열,행' 형식으로 입력하세요. (예: 5,1)";
    private static final String ERROR_NUMBER_FORMAT = "'%s'는 숫자가 아닙니다. 숫자만 입력하세요. (예: 5,1)";

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
            throw new IllegalArgumentException(ERROR_BLANK_INPUT);
        }
    }

    private static List<String> splitAndTrim(String input) {
        return Arrays.stream(input.split(DELIMITER))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private static void validateTokenCount(List<String> tokens) {
        if (tokens.size() != REQUIRED_TOKEN_COUNT) {
            throw new IllegalArgumentException(String.format(ERROR_TOKEN_COUNT, tokens.size()));
        }
    }

    private static void validateNoBlankToken(List<String> tokens) {
        IntStream.range(0, tokens.size())
                .filter(i -> tokens.get(i).isBlank())
                .findFirst()
                .ifPresent(i -> {
                    throw new IllegalArgumentException(
                            String.format(ERROR_BLANK_TOKEN, i + 1)
                    );
                });
    }

    private static void validateNumberFormat(List<String> tokens) {
        for (String token : tokens) {
            if (!NUMBER_PATTERN.matcher(token).matches()) {
                throw new IllegalArgumentException(String.format(ERROR_NUMBER_FORMAT, token));
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
