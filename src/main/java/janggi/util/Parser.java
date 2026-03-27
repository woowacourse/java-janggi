package janggi.util;

import java.util.Arrays;
import java.util.List;

public final class Parser {

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
            throw new IllegalArgumentException("[ERROR] 숫자만 입력 가능합니다");
        }
    }
}
