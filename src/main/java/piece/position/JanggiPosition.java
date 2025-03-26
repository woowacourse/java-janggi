package piece.position;

import java.util.Objects;
import move.direction.Direction;

public class JanggiPosition {

    private final int row;
    private final int column;

    public JanggiPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public JanggiPosition add(Direction direction) {
        int newRow = this.row + direction.y();
        int newColumn = this.column + direction.x();

        return new JanggiPosition(newRow, newColumn);
    }

    public boolean isSameRow(JanggiPosition position) {
        return this.row == position.row;
    }

    public boolean isSameColumn(JanggiPosition position) {
        return this.column == position.column;
    }

    private boolean isAbsoluteBigger(JanggiPosition position) {
        return (this.row + this.column) - (position.row + position.column) > 0;
    }

    public boolean isSameDiagonal(JanggiPosition position) {
        return (row - position.row) == (column - position.column);
    }

    public JanggiPosition getBiggerPosition(JanggiPosition otherPosition) {
        if (this.isAbsoluteBigger(otherPosition)) {
            return this;
        }
        return otherPosition;
    }

    public JanggiPosition getSmallerPosition(JanggiPosition otherPosition) {
        if (this.isAbsoluteBigger(otherPosition)) {
            return otherPosition;
        }
        return this;
    }

    public boolean isInsideGungsung(JanggiPosition position) {
        return GungsungPosition.isInsideGungsung(position);
    }

    public boolean isPositionDiagonalGungPosition() {
        return GungsungPosition.isPositionDiagonalGungPosition(this);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JanggiPosition position = (JanggiPosition) o;
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

