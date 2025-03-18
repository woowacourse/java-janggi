package janggi.point;

import java.util.Objects;

public class Point {

    private final int row;
    private final int column;

    public Point(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Point move(int rowMovingDistance, int columnMovingDistance) {
        return new Point(this.row + rowMovingDistance, this.column + columnMovingDistance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point point = (Point) o;
        return row == point.row && column == point.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
