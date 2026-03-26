package domain.board;

public record Intersection(
        int row,
        int file
) {

    private static final int MINIMUM_ROW = 1;
    private static final int MAXIMUM_ROW = 10;
    private static final int MINIMUM_FILE = 1;
    private static final int MAXIMUM_FILE = 9;

    public boolean isOutOfBoard() {
        return isRowOutOfBoard() || isFileOutOfBoard();
    }

    public boolean isInBoard() {
        return !isOutOfBoard();
    }

    private boolean isRowOutOfBoard() {
        return row < MINIMUM_ROW || row > MAXIMUM_ROW;
    }

    private boolean isFileOutOfBoard() {
        return file < MINIMUM_FILE || file > MAXIMUM_FILE;
    }
}
