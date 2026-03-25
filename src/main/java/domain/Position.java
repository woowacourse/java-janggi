package domain;

public record Position(int x, int y) {

    private static final int MIN_X = 1;
    private static final int MAX_X = 9;
    private static final int MIN_Y = 1;
    private static final int MAX_Y = 10;
    public static final String ERROR_INVALID_X_COORDINATE = "[ERROR] x 좌표가 올바르지 않습니다.";
    public static final String ERROR_INVALID_Y_COORDINATE = "[ERROR] y 좌표가 올바르지 않습니다.";

    public Position {
        if (x < MIN_X || x > MAX_X) {
            throw new IllegalArgumentException(ERROR_INVALID_X_COORDINATE);
        }

        if (y < MIN_Y || y > MAX_Y) {
            throw new IllegalArgumentException(ERROR_INVALID_Y_COORDINATE);
        }
    }
}
