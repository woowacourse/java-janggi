package position;

import java.util.Objects;
import route.Direction;

public final class Position {
    private final Column column;
    private final Row row;

    public Position(Column column, Row row) {
        this.column = column;
        this.row = row;
    }

    public boolean canMove(Direction direction, Board board) {
        if (column.canMove(direction.column()) && row.canMove(direction.row())) {
            return board.isBlank(new Position(column.move(direction.column()), row.move(direction.row())));
        }
        return false;
    }

    public boolean canMoveLast(Direction direction, Board board) {
        if (column.canMove(direction.column()) && row.canMove(direction.row())) {
            return board.canMoveLast(new Position(column.move(direction.column()), row.move(direction.row())));
        }
        return false;
    }

    public Position move(Direction direction) {
        return new Position(column.move(direction.column()), row.move(direction.row()));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return column == position.column && row == position.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }

    public Column getColumn() {
        return column;
    }

    public Row getRow() {
        return row;
    }

}
