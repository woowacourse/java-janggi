package domain.board;

import domain.vo.Position;

public enum Palace {

    CHU(0, 2, 3, 5),
    HAN(7, 9, 3, 5);

    private final int minRow;
    private final int maxRow;
    private final int minCol;
    private final int maxCol;

    Palace(int minRow, int maxRow, int minCol, int maxCol) {
        this.minRow = minRow;
        this.maxRow = maxRow;
        this.minCol = minCol;
        this.maxCol = maxCol;
    }

    public boolean isInPalace(Position position) {
        return position.getRow() >= minRow && position.getRow() <= maxRow
                && position.getCol() >= minCol && position.getCol() <= maxCol;
    }

    public boolean isDiagonalPoint(final Position position) {
        int row = position.getRow();
        int col = position.getCol();

        return (row == minRow && col == minCol)
                || (row == minRow && col == maxCol)
                || (row == maxRow && col == minCol)
                || (row == maxRow && col == maxCol)
                || isCenter(position);
    }

    private boolean isCenter(final Position position) {
        int centerRow = (minRow + maxRow) / 2;
        int centerCol = (minCol + maxCol) / 2;

        return position.getRow() == centerRow && position.getCol() == centerCol;
    }
}
