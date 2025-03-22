package model;


import java.util.List;
import java.util.Objects;

public class Position {

    private final Column column;
    private final Row row;

    public Position(Column column, Row row) {
        this.column = column;
        this.row = row;
    }

    public Position(List<Integer> columnAndRow) {
        this.column = Column.getColumnBy(columnAndRow.getFirst());
        this.row = Row.getRowBy(columnAndRow.getLast());
    }

    public Position moveUp() {
        return new Position(column, row.down());
    }

    public boolean canMoveUp() {
        return row.canDown();
    }

    public Position moveDown() {
        return new Position(column, row.up());
    }

    public boolean canMoveDown() {
        return row.canUp();
    }

    public Position moveLeft() {
        return new Position(column.down(), row);
    }

    public boolean canMoveLeft() {
        return column.canDown();
    }

    public Position moveRight() {
        return new Position(column.up(), row);
    }

    public boolean canMoveRight() {
        return column.canUp();
    }

    public Position moveUpRight() {
        return new Position(column.up(), row.down());
    }

    public boolean canMoveUpRight() {
        return column.canUp() && row.canDown();
    }

    public Position moveUpLeft() {
        return new Position(column.down(), row.down());
    }

    public boolean canMoveUpLeft() {
        return column.canDown() && row.canDown();
    }

    public Position moveDownRight() {
        return new Position(column.up(), row.up());
    }

    public boolean canMoveDownRight() {
        return column.canUp() && row.canUp();
    }

    public Position moveDownLeft() {
        return new Position(column.down(), row.up());
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
}
