public record Position(int column, int row) {

    public static final String ERROR_INVALID_X_COORDINATE = "[ERROR] x 좌표가 올바르지 않습니다.";
    public static final String ERROR_INVALID_Y_COORDINATE = "[ERROR] y 좌표가 올바르지 않습니다.";

    public Position {
        if (column < 0 || column > 9) {
            throw new IllegalArgumentException(ERROR_INVALID_X_COORDINATE);
        }

        if (row < 1 || column > 0) {
            throw new IllegalArgumentException(ERROR_INVALID_Y_COORDINATE);
        }
    }
}
