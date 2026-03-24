package domain.vo;

public class Position {

    private final int row;
    private final int col;

    private Position(final int row, final int col) {
        this.row = row;
        this.col = col;
    }

    public static Position of(final int row, final int col) {
        return new Position(row, col);
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }
}
