package janggi.position;

import janggi.route.Direction;
import janggi.route.Route;
import java.util.Objects;

public final class Position {
    private final Column column;
    private final Row row;

    public Position(Column column, Row row) {
        this.column = column;
        this.row = row;
    }

    public Position(String columnIndex, String rowIndex) {
        this.column = Column.convert(columnIndex);
        this.row = Row.convert(rowIndex);
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

    public boolean canMoveLastForCannon(Direction direction, Board board) {
        if (column.canMove(direction.column()) && row.canMove(direction.row())) {
            return board.canMoveLastForCannon(new Position(column.move(direction.column()), row.move(direction.row())));
        }
        return false;
    }

    public boolean canJump(Direction direction, Board board) {
        if (column.canMove(direction.column()) && row.canMove(direction.row())) {
            Position target = new Position(column.move(direction.column()), row.move(direction.row()));
            return (board.hasPieceWithoutCannon(target) && target.canMove(direction,
                    board));
        }
        return false;
    }

    public boolean canMove(Route route, Board board) {
        Position target = this;
        for (Direction direction : route.route()) {
            if (!target.canMove(direction, board)) {
                return false;
            }
            target = target.move(direction);
        }
        return true;
    }

    public Position move(Direction direction) {
        return new Position(column.move(direction.column()), row.move(direction.row()));
    }

    public Position move(Route route) {
        Position source = this;
        for (Direction direction : route.route()) {
            source = source.move(direction);
        }
        return source;
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

    public Row row() {
        return row;
    }

    public Column column(){
        return column;
    }
}
