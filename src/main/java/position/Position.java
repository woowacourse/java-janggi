package position;

import java.util.Objects;

public class Position {
    private final Row row;
    private final Column column;

    public Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public Position(int row, int column) {
        this.row = new Row(row);
        this.column = new Column(column);
    }

    public int row() {
        return row.index();
    }

    public int column() {
        return column.index();
    }

    public Position moveUp() {
        return new Position(row.up(), column);
    }

    public Position moveDown() {
        return new Position(row.down(), column);
    }

    public Position moveRight() {
        return new Position(row, column.right());
    }

    public Position moveLeft() {
        return new Position(row, column.left());
    }

    @Override
    public boolean equals(Object o) {
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
