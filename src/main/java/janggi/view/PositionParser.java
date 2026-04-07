package janggi.view;

import java.util.Arrays;
import java.util.List;

public class PositionParser {
    private static final String BASE_DELIMITER = ",";
    private static final String INVALID_POSITION_TYPE = "좌표는 숫자만 입력 가능합니다.";

    public static List<Integer> parsePositionInput(String input) {
        try {
            return parseString(input).stream()
                    .map(Integer::parseInt)
                    .toList();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_POSITION_TYPE);
        }
    }

    private static List<String> parseString(String input) {
        return Arrays.stream(input.split(BASE_DELIMITER))
                .map(String::trim)
                .toList();
    }
}

