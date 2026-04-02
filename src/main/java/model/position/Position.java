package model.position;

import model.move.Direction;

public record Position(Row row, Column column) {
    public static Position of(int x, int y) {
        return new Position(Row.from(x), Column.from(y));
    }

    public Position move(Direction direction) {
        return Position.of(direction.moveRow(row.value()), direction.moveCol(column.value()));
    }

    public boolean isSamePosition(Position to) {
        return this.equals(to);
    }

    public boolean isSameRow(Position to) {
        return this.row.equals(to.row());
    }

    public boolean isSameColumn(Position to) {
        return this.column.equals(to.column());
    }
}
