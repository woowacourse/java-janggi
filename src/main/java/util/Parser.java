package util;

import domain.position.Position;

public class Parser {

    public static int parseToPlacementCode(String input) {
        if (!input.matches("^[1|2|3|4]$")) {
            throw new IllegalArgumentException("코드는 1, 2, 3, 4만 입력 가능합니다.");
        }
        return Integer.parseInt(input);
    }

    public static int parseToSideCode(String input) {
        if (!input.matches("^[1|2]$")) {
            throw new IllegalArgumentException("코드는 1, 2만 입력 가능합니다.");
        }
        return Integer.parseInt(input);
    }

    public static Position parseToPosition(String input) {
        String[] values = input.split(",");
        validatePositionFormat(values);
        try {
            int row = Integer.parseInt(values[0].trim());
            int column = Integer.parseInt(values[1].trim());

            return Position.of(row, column);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("숫자 형태만 입력 가능합니다.");
        }
    }

    private static void validatePositionFormat(String[] values) {
        if (values.length != 2) {
            throw new IllegalArgumentException("쉼표로 구분해주세요.");
        }
    }
}
