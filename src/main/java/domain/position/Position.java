package domain.position;

import static common.Constant.MAX_COLUMN;
import static common.Constant.MAX_ROW;
import static common.Constant.MIN_COLUMN;
import static common.Constant.MIN_ROW;

import domain.place.moveStrategy.Direction;
import java.util.Objects;

public class Position {

    private final Row row;
    private final Column column;

    public Position(int row, int column) {
        this.row = new Row(row);
        this.column = new Column(column);
    }

    public static boolean isNotOutOfBounds(int row, int column) {
        return row >= MIN_ROW && row <= MAX_ROW && column >= MIN_COLUMN && column <= MAX_COLUMN;
    }

    public boolean isNotStraightLine(Position position) {
        return getRow() != position.getRow() && getColumn() != position.getColumn();
    }

    public int getRow() {
        return row.getRow();
    }

    public int getColumn() {
        return column.getColumn();
    }

    public Position move(Direction direction) {
        return new Position(row.getRow() + direction.getRow(), column.getColumn() + direction.getColumn());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position)) {
            return false;
        }
        Position position = (Position) o;
        return row.equals(position.row) && column.equals(position.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
