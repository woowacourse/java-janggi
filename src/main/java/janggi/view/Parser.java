package janggi.view;

import java.util.Arrays;
import java.util.List;

public final class Parser {

    private static final String ONLY_NUMBERS_ALLOWED = "[ERROR] 숫자만 입력 가능합니다";

    private Parser() {
    }

    public static List<Integer> parseByDelimiter(String delimiter, String input) {
        return Arrays.stream(input.split(delimiter))
                .map(String::strip)
                .map(Parser::parseToInt)
                .toList();
    }

    private static int parseToInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException numberFormatException) {
            throw new IllegalArgumentException(ONLY_NUMBERS_ALLOWED);
        }
    }

    public static long parseToLong(String number) {
        try {
            return Long.parseLong(number);
        } catch (NumberFormatException numberFormatException) {
            throw new IllegalArgumentException(ONLY_NUMBERS_ALLOWED);
        }
    }
}
