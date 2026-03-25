package domain;

public record Position(int column, int row) {

    private static final int MIN_COLUMN = 0;
    private static final int MAX_COLUMN = 9;
    private static final int MIN_ROW = 1;
    private static final int MAX_ROW = 9;
    public static final String ERROR_INVALID_X_COORDINATE = "[ERROR] x 좌표가 올바르지 않습니다.";
    public static final String ERROR_INVALID_Y_COORDINATE = "[ERROR] y 좌표가 올바르지 않습니다.";

    public Position {
        if (column < MIN_COLUMN || column > MAX_COLUMN) {
            throw new IllegalArgumentException(ERROR_INVALID_X_COORDINATE);
        }

        if (row < MIN_ROW || row > MAX_ROW) {
            throw new IllegalArgumentException(ERROR_INVALID_Y_COORDINATE);
        }
    }
}
