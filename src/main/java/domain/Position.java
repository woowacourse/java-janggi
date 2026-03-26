package domain;

import java.util.Objects;

public class Position {
    private final Row row;
    private final Column column;

    public Position(int row, int column) {
        this.row = new Row(row);
        this.column = new Column(column);
    }

    public boolean isSameRow(Position other) {
        return other.row.equals(this.row);
    }

    public boolean isSameCol(Position other) {
        return other.column.equals(this.column);
    }

    public int rowDiff(Position other) {
        return this.row.diff(other.row);
    }

    public int columnDiff(Position other) {
        return this.column.diff(other.column);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
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
