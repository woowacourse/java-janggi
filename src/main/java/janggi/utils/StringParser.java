package janggi.utils;

import java.util.Arrays;
import java.util.List;

public class StringParser {

    private static final String BLANK = " ";

    private StringParser() {
    }

    public static int parseInt(final String input) {
        try {
            return Integer.parseInt(input);
        } catch (final NumberFormatException exception) {
            throw new IllegalArgumentException("[ERROR] 숫자가 아닙니다.");
        }
    }

    public static List<String> split(final String input) {
        return Arrays.asList(input.split(BLANK));
    }

    public static List<Integer> parseInt(final List<String> tokens) {
        try {
            final List<Integer> parsedTokens = tokens.stream()
                    .map(Integer::parseInt)
                    .toList();
            if (parsedTokens.size() != 2) {
                throw new IllegalArgumentException("[ERROR] 두개의 좌표를 입력해야 합니다.");
            }
            return parsedTokens;
        } catch (final NumberFormatException exception) {
            throw new IllegalArgumentException("[ERROR] 좌표는 숫자여야 합니다.");
        }
    }
}
