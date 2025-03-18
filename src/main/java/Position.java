public class Position {
    private final int row;
    private final int column;

    public Position(int row, int column) {
        validatePosition(row, column);
        this.row = row;
        this.column = column;
    }

    private void validatePosition(int row, int column) {
        if (row < 0 || column < 0) {
            throw new IllegalArgumentException();
        }
    }

}
