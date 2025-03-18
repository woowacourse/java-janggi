package domain;

import java.util.Objects;

public final class JanggiCoordinate implements Coordinate{
    private int row;
    private int col;

    public JanggiCoordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public Coordinate moveForward(){
        return new JanggiCoordinate(row + 1,col);
    }

    @Override
    public Coordinate moveDiagonal(){
        return new JanggiCoordinate(row + 1, col +1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JanggiCoordinate that = (JanggiCoordinate) o;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
