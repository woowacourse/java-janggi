package model;


import java.util.Collections;
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

    public List<Position> findUpDirection(Position arrival) {
        if (this.canMoveUp() && this.moveUp().equals(arrival)) {
            return List.of(this.moveUp());
        }
        return Collections.emptyList();
    }

    public List<Position> findDownDirection( Position arrival) {
        if (this.canMoveDown() && this.moveDown().equals(arrival)) {
            return List.of(this.moveDown());
        }
        return Collections.emptyList();
    }

    public List<Position> findLeftDirection(Position arrival) {
        if (this.canMoveLeft() && this.moveLeft().equals(arrival)) {
            return List.of(this.moveLeft());
        }
        return Collections.emptyList();
    }

    public List<Position> findRightDirection(Position arrival) {
        if (this.canMoveRight() && this.moveRight().equals(arrival)) {
            return List.of(this.moveRight());
        }
        return Collections.emptyList();
    }

    private Position moveUp() {
        return new Position(column.down(), row);
    }

    private boolean canMoveUp() {
        return column.canDown();
    }

    private Position moveDown() {
        return new Position(column.up(), row);
    }

    private boolean canMoveDown() {
        return column.canUp();
    }

    private Position moveLeft() {
        return new Position(column, row.down());
    }

    private boolean canMoveLeft() {
        return row.canDown();
    }

    private Position moveRight() {
        return new Position(column, row.up());
    }

    private boolean canMoveRight() {
        return row.canUp();
    }

    private Position moveUpRight() {
        return new Position(column.down(), row.up());
    }

    private boolean canMoveUpRight() {
        return column.canDown() && row.canUp();
    }

    private Position moveUpLeft() {
        return new Position(column.down(), row.down());
    }

    private boolean canMoveUpLeft() {
        return column.canDown() && row.canDown();
    }

    private Position moveDownRight() {
        return new Position(column.up(), row.up());
    }

    private boolean canMoveDownRight() {
        return column.canUp() && row.canUp();
    }

    private Position moveDownLeft() {
        return new Position(column.up(), row.down());
    }

    private boolean canDownLeft() {
        return column.canUp() && row.canDown();
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
