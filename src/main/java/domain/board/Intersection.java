package domain.board;

public record Intersection(int row, int file) {

    private static final int LOWER_BOUND_ROW = 1;
    private static final int UPPER_BOUND_ROW = 10;
    private static final int LOWER_BOUND_FILE = 1;
    private static final int UPPER_BOUND_FILE = 9;

    public boolean isOutOfBounds() {
        return isOutOfRow() || isOutOfFile();
    }

    public boolean isInBounds() {
        return !isOutOfBounds();
    }

    private boolean isOutOfRow() {
        return row < LOWER_BOUND_ROW || row > UPPER_BOUND_ROW;
    }

    private boolean isOutOfFile() {
        return file < LOWER_BOUND_FILE || file > UPPER_BOUND_FILE;
    }
}
