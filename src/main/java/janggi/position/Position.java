package janggi.position;

public record Position(int row, int col) {

    private static final int BOARD_ROW_SIZE = 10;
    private static final int BOARD_COL_SIZE = 9;

    public Position {
        validateOutOfBound(row, col);
    }

    private void validateOutOfBound(final int row, final int col) {
        if ((row < 0 || row >= BOARD_ROW_SIZE) || (col < 0 || col >= BOARD_COL_SIZE)) {
            throw new IllegalArgumentException("[ERROR] 장기판은 10 x 9 입니다. 범위를 초과하였습니다.");
        }
    }

    public boolean isOneStep(final Position position) {
        return calculateDifferenceRow(position.row) + calculateDifferenceCol(position.col) == 1;
    }

    public boolean isBehind(final Position position) {
        final int dx = position.row - row;
        final int dy = calculateDifferenceCol(position.col);

        return dx == 0 && dy == 1 || dx == 1 && dy == 0;
    }

    public int calculateDifferenceRow(final int row) {
        return Math.abs(row - this.row);
    }

    public int calculateDifferenceCol(final int col) {
        return Math.abs(col - this.col);
    }

}
