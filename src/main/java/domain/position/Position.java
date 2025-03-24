package domain.position;

import java.util.Objects;

public class Position {

    private final Row row;
    private final Column column;

    private Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public static Position of(int row, int column) {
        return new Position(new Row(row), new Column(column));
    }

    public Position moveDirection(Direction direction) {
        return movePosition(direction.getDeltaRow(), direction.getDeltaColumn());
    }

    public boolean canMoveDirection(Direction direction){
        return canMovePosition(direction.getDeltaRow(), direction.getDeltaColumn());
    }

    private Position movePosition(int deltaRow, int deltaColumn) {
        return new Position(row.move(deltaRow), column.move(deltaColumn));
    }

    private boolean canMovePosition(int deltaRow, int deltaColumn) {
        return row.canMove(deltaRow) && column.canMove(deltaColumn);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return Objects.equals(row, position.row) && Objects.equals(column, position.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
