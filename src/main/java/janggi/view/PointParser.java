package janggi.view;

import janggi.board.point.Point;

public final class PointParser {

    private static final int VALID_LENGTH = 2;
    private static final int X_INDEX = 0;
    private static final int Y_INDEX = 1;

    private PointParser() {
    }

    public static Point parse(String value) {
        validateInput(value);
        return convertToPoint(value);
    }

    private static void validateInput(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("입력이 비어있습니다.");
        }
        if (value.length() != VALID_LENGTH) {
            throw new IllegalArgumentException("잘못된 좌표 입력입니다. 입력: %s".formatted(value));
        }
    }

    private static Point convertToPoint(String value) {
        try {
            int x = Integer.parseInt(String.valueOf(value.charAt(X_INDEX)));
            int y = Integer.parseInt(String.valueOf(value.charAt(Y_INDEX)));
            return new Point(x, y);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력 가능합니다. 입력: %s".formatted(value));
        }
    }
}
