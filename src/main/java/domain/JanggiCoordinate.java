package domain;

import java.util.Objects;

public final class JanggiCoordinate {
    private final int row;
    private final int col;

    public JanggiCoordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public JanggiCoordinate moveUp() {
        return new JanggiCoordinate(this.row, this.col - 1);
    }

    public JanggiCoordinate moveDown() {
        return new JanggiCoordinate(this.row, this.col + 1);
    }

    public JanggiCoordinate moveRight() {
        return new JanggiCoordinate(this.row + 1, this.col);
    }

    public JanggiCoordinate moveLeft() {
        return new JanggiCoordinate(this.row - 1, this.col);
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

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
