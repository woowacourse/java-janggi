package janggi.utils;

import janggi.global.Pair;
import java.util.Arrays;
import java.util.List;

public final class Parser {

    private static final int REQUIRED_POSITION_ARGS_SIZE = 2;
    private static final String POSITION_DELIMITER = ",";

    private Parser() {
    }

    public static int parseInteger(final String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("정수만 입력할 수 있습니다.");
        }
    }

    public static Pair<Integer, Integer> parsePosition(final String input) {
        try {
            final List<Integer> parsed = Arrays.stream(input.split(POSITION_DELIMITER))
                .map(Parser::parseInteger)
                .toList();
            validateRawPosition(parsed);
            return new Pair<>(parsed.get(0), parsed.get(1));
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("위치는 행,열 형식으로 입력되어야 합니다.");
        }
    }

    private static void validateRawPosition(final List<Integer> parsed) {
        if (parsed.size() != REQUIRED_POSITION_ARGS_SIZE) {
            throw new IllegalArgumentException("위치는 행,열 형식으로 입력되어야 합니다.");
        }
    }
}
