package model;


import java.util.ArrayList;
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

    public List<Position> findDownDirection(Position arrival) {
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

    public List<Position> findUpDirectionUntilEnd(Position arrival) {
        List<Position> temporaryDirection = new ArrayList<>();
        Position movedPosition = this.copyOf();
        while (movedPosition.canMoveUp()) {
            movedPosition = movedPosition.moveUp();
            temporaryDirection.add(movedPosition);
            if (movedPosition.equals(arrival)) {
                return temporaryDirection;
            }
        }
        return Collections.emptyList();
    }

    public List<Position> findDownDirectionUntilEnd(Position arrival) {
        List<Position> temporaryDirection = new ArrayList<>();
        Position movedPosition = this.copyOf();
        while (movedPosition.canMoveDown()) {
            movedPosition = movedPosition.moveDown();
            temporaryDirection.add(movedPosition);
            if (movedPosition.equals(arrival)) {
                return temporaryDirection;
            }
        }
        return Collections.emptyList();
    }

    public List<Position> findLeftDirectionUntilEnd(Position arrival) {
        List<Position> temporaryDirection = new ArrayList<>();
        Position movedPosition = this.copyOf();
        while (movedPosition.canMoveLeft()) {
            movedPosition = movedPosition.moveLeft();
            temporaryDirection.add(movedPosition);
            if (movedPosition.equals(arrival)) {
                return temporaryDirection;
            }
        }
        return Collections.emptyList();
    }

    public List<Position> findRightDirectionUntilEnd(Position arrival) {
        List<Position> temporaryDirection = new ArrayList<>();
        Position movedPosition = this.copyOf();
        while (movedPosition.canMoveRight()) {
            movedPosition = movedPosition.moveRight();
            temporaryDirection.add(movedPosition);
            if (movedPosition.equals(arrival)) {
                return temporaryDirection;
            }
        }
        return Collections.emptyList();
    }

    public List<Position> findUpAndUpRight(Position arrival) {
        List<Position> temporaryDirection = new ArrayList<>();
        Position movedPosition = this.copyOf();
        if (movedPosition.canMoveUp()) {
            movedPosition = movedPosition.moveUp();
            temporaryDirection.add(movedPosition);
        }
        if (movedPosition.canMoveUpRight()) {
            Position nextMovedPosition = movedPosition.moveUpRight();
            if (arrival.equals(nextMovedPosition)) {
                temporaryDirection.add(nextMovedPosition);
                return temporaryDirection;
            }
        }
        return Collections.emptyList();
    }

    public List<Position> findUpAndUpLeft(Position arrival) {
        List<Position> temporaryDirection = new ArrayList<>();
        Position movedPosition = this.copyOf();
        if (movedPosition.canMoveUp()) {
            movedPosition = movedPosition.moveUp();
            temporaryDirection.add(movedPosition);
        }
        if (movedPosition.canMoveUpLeft()) {
            Position nextMovedPosition = movedPosition.moveUpLeft();
            if (arrival.equals(nextMovedPosition)) {
                temporaryDirection.add(nextMovedPosition);
                return temporaryDirection;
            }
        }
        return Collections.emptyList();
    }

    public List<Position> findDownAndDownRight(Position arrival) {
        List<Position> temporaryDirection = new ArrayList<>();
        Position movedPosition = this.copyOf();
        if (movedPosition.canMoveDown()) {
            movedPosition = movedPosition.moveDown();
            temporaryDirection.add(movedPosition);
        }
        if (movedPosition.canMoveDownRight()) {
            Position nextMovedPosition = movedPosition.moveDownRight();
            if (arrival.equals(nextMovedPosition)) {
                temporaryDirection.add(nextMovedPosition);
                return temporaryDirection;
            }
        }
        return Collections.emptyList();
    }

    public List<Position> findDownAndDownLeft(Position arrival) {
        List<Position> temporaryDirection = new ArrayList<>();
        Position movedPosition = this.copyOf();
        if (movedPosition.canMoveDown()) {
            movedPosition = movedPosition.moveDown();
            temporaryDirection.add(movedPosition);
        }
        if (movedPosition.canMoveDownLeft()) {
            Position nextMovedPosition = movedPosition.moveDownLeft();
            if (arrival.equals(nextMovedPosition)) {
                temporaryDirection.add(nextMovedPosition);
                return temporaryDirection;
            }
        }
        return Collections.emptyList();
    }

    public List<Position> findLeftAndUpLeft(Position arrival) {
        List<Position> temporaryDirection = new ArrayList<>();
        Position movedPosition = this.copyOf();
        if (movedPosition.canMoveLeft()) {
            movedPosition = movedPosition.moveLeft();
            temporaryDirection.add(movedPosition);
        }
        if (movedPosition.canMoveUpLeft()) {
            Position nextMovedPosition = movedPosition.moveUpLeft();
            if (arrival.equals(nextMovedPosition)) {
                temporaryDirection.add(nextMovedPosition);
                return temporaryDirection;
            }
        }
        return Collections.emptyList();
    }

    public List<Position> findLeftAndDownLeft(Position arrival) {
        List<Position> temporaryDirection = new ArrayList<>();
        Position movedPosition = this.copyOf();
        if (movedPosition.canMoveLeft()) {
            movedPosition = movedPosition.moveLeft();
            temporaryDirection.add(movedPosition);
        }
        if (movedPosition.canMoveDownLeft()) {
            Position nextMovedPosition = movedPosition.moveDownLeft();
            if (arrival.equals(nextMovedPosition)) {
                temporaryDirection.add(nextMovedPosition);
                return temporaryDirection;
            }
        }
        return Collections.emptyList();
    }

    public List<Position> findRightAndUpRight(Position arrival) {
        List<Position> temporaryDirection = new ArrayList<>();
        Position movedPosition = this.copyOf();
        if (movedPosition.canMoveRight()) {
            movedPosition = movedPosition.moveRight();
            temporaryDirection.add(movedPosition);
        }
        if (movedPosition.canMoveUpRight()) {
            Position nextMovedPosition = movedPosition.moveUpRight();
            if (arrival.equals(nextMovedPosition)) {
                temporaryDirection.add(nextMovedPosition);
                return temporaryDirection;
            }
        }
        return Collections.emptyList();
    }

    public List<Position> findRightAndDownRight(Position arrival) {
        List<Position> temporaryDirection = new ArrayList<>();
        Position movedPosition = this.copyOf();
        if (movedPosition.canMoveRight()) {
            movedPosition = movedPosition.moveRight();
            temporaryDirection.add(movedPosition);
        }
        if (movedPosition.canMoveDownRight()) {
            Position nextMovedPosition = movedPosition.moveDownRight();
            if (arrival.equals(nextMovedPosition)) {
                temporaryDirection.add(nextMovedPosition);
                return temporaryDirection;
            }
        }
        return Collections.emptyList();
    }

    public Position moveUp() {
        return new Position(column.down(), row);
    }

    public boolean canMoveUp() {
        return column.canDown();
    }

    public Position moveDown() {
        return new Position(column.up(), row);
    }

    public boolean canMoveDown() {
        return column.canUp();
    }

    public Position moveLeft() {
        return new Position(column, row.down());
    }

    public boolean canMoveLeft() {
        return row.canDown();
    }

    public Position moveRight() {
        return new Position(column, row.up());
    }

    public boolean canMoveRight() {
        return row.canUp();
    }

    public Position moveUpRight() {
        return new Position(column.down(), row.up());
    }

    public boolean canMoveUpRight() {
        return column.canDown() && row.canUp();
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
        return new Position(column.up(), row.down());
    }

    public boolean canMoveDownLeft() {
        return column.canUp() && row.canDown();
    }

    public Position copyOf() {
        return new Position(this.column, this.row);
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
