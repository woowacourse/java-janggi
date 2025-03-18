package domain;

import java.util.Objects;

public final class JanggiCoordinate implements Coordinate {
    private int row;
    private int col;

    public JanggiCoordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public Coordinate move(int increaseRow, int increaseCol) {
        return new JanggiCoordinate(row + increaseRow, col + increaseCol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JanggiCoordinate that = (JanggiCoordinate) o;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
