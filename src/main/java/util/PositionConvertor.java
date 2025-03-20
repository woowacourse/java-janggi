package util;

import domain.Position;

public class PositionConvertor {
    private static final String REGEX = "^[a-z]\\d$";

    public static Position changeInputToPosition(String input) {
        validatePositionInput(input);
        return Position.of(Character.getNumericValue(input.charAt(1)), input.charAt(0) - 'a');
    }

    private static void validatePositionInput(String input) {
        if (input.matches(REGEX)) {
            return;
        }
        throw new IllegalArgumentException("올바른 좌표 형식이 아닙니다.");
    }
}
