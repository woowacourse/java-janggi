package object;

import java.util.Objects;

public class Coordinate {

    private final int row;
    private final int column;

    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Coordinate add(Coordinate coordinate) {
        int newRow = this.row + coordinate.row;
        int newColumn = this.column + coordinate.column;

        return new Coordinate(newRow, newColumn);
    }

    public boolean isSameRow(Coordinate coordinate) {
        return this.row == coordinate.row;
    }

    public boolean isSameColumn(Coordinate coordinate) {
        return this.column == coordinate.column;
    }

    private boolean isAbsoluteBigger(Coordinate coordinate) {
        return (this.row + this.column) - (coordinate.row + coordinate.column) > 0;
    }

    public static Coordinate getMinPosition(Coordinate coordinate, Coordinate otherCoordinate) {
        if (coordinate.isAbsoluteBigger(otherCoordinate)) {
            return otherCoordinate;
        }
        return coordinate;
    }

    public static Coordinate getMaxPosition(Coordinate coordinate, Coordinate otherCoordinate) {
        if (coordinate.isAbsoluteBigger(otherCoordinate)) {
            return coordinate;
        }
        return otherCoordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coordinate coordinate = (Coordinate) o;
        return row == coordinate.row && column == coordinate.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}

