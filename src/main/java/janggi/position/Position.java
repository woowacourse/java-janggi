package janggi.position;

import java.util.Objects;
import janggi.route.Direction;
import janggi.route.Route;

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

    public boolean canMove(Route route, Board board) {
        for (Direction direction : route.route()) {
            if(!canMove(direction, board)){
                return false;
            }
            move(direction);
        }
        return true;
    }

    public Position move(Direction direction) {
        return new Position(column.move(direction.column()), row.move(direction.row()));
    }

    public Position move(Route route) {
        Position destination = this;
        for (Direction direction : route.route()) {
            destination = destination.move(direction);
        }
        return destination;
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

    public void print() {
        System.out.print("row = " + row);
        System.out.print("    column = " + column);
        System.out.println();
    }

}
