package domain;

import java.awt.Point;
import java.util.Objects;

public class Position {

    private final Point point;
    public static final int MIN_ROW = 1;
    public static final int MIN_COLUMN = 1;
    public static final int MAX_ROW = 10;
    public static final int MAX_COLUMN = 9;

    public Position(final int column, final int row) {
//        if (x < 1 || y < 1 || x > 9 || y > 10) {
//            throw new IllegalArgumentException("위치는 장기판 내부여야 합니다.");
//        }
        this.point = new Point(column, row);
    }

    public int getColumn() {
        return point.x;
    }

    public int getRow() {
        return point.y;
    }

    public boolean isInValidPosition() {
        int nextColumn = this.getColumn();
        int nextRow = this.getRow();
        return (nextColumn < MIN_COLUMN ||nextColumn > MAX_COLUMN ||
                nextRow < MIN_ROW || nextRow > MAX_ROW
        );
    }

    public Position nextPosition(Direction direction) {
        return new Position(
                getColumn() + direction.getDeltaColumn(),
                getRow() + direction.getDeltaRow()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position position)) {
            return false;
        }
        return Objects.equals(point, position.point);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(point);
    }

    @Override
    public String toString() {
        return "\nPosition{" +
                "point=" + point +
                '}';
    }
}
