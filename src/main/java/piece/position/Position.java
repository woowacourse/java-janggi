package piece.position;

import java.util.Objects;
import move.direction.Direction;

public class Position {

    private final int row;
    private final int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Position add(Direction direction) {
        int newRow = this.row + direction.y();
        int newColumn = this.column + direction.x();

        return new Position(newRow, newColumn);
    }

    public boolean isSameRow(Position position) {
        return this.row == position.row;
    }

    public boolean isSameColumn(Position position) {
        return this.column == position.column;
    }

    private boolean isAbsoluteBigger(Position position) {
        return (this.row + this.column) - (position.row + position.column) > 0;
    }

    public Position getBiggerPosition(Position otherPosition) {
        if (this.isAbsoluteBigger(otherPosition)) {
            return this;
        }
        return otherPosition;
    }

    public Position getSmallerPosition(Position otherPosition) {
        if (this.isAbsoluteBigger(otherPosition)) {
            return otherPosition;
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}

