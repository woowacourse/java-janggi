package domain;

import java.util.Objects;

public class Position {
    public static final int MAX_ROW=10;
    public static final int MAX_COL=9;
    public static final int MIN_ROW_COL=1;

    private final int row;
    private final int col;

    private Position(int row, int col) {
        validateBoardSize(row, col);
        this.row = row;
        this.col = col;
    }

    public static Position create(int row, int col) {
        return new Position(row, col);
    }

    private void validateBoardSize(int row, int col){
        if (row < MIN_ROW_COL || row > MAX_ROW || col < MIN_ROW_COL || col > MAX_COL) {
            throw new IllegalArgumentException("좌표 범위를 벗어났습니다.");
        }
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
