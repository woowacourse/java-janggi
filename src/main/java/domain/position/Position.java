package domain.position;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Position {
    private final Row row;
    private final Column column;

    public Position(int row, int column) {
        this.row = new Row(row);
        this.column = new Column(column);
    }

    public Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public static Position from(String row, String column) {
        try {
            return new Position(Integer.parseInt(row), Integer.parseInt(column));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("위치는 숫자로 입력해주세요.");
        }
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

    public List<Position> betweenSameRow(Position other) {
        List<Position> routes = new ArrayList<>();
        Column start = other.column.min(this.column);
        Column dest = other.column.max(this.column);
        for (Column column = start.next(); column.isLessThan(dest); column = column.next()) {
            routes.add(new Position(other.row, column));
        }
        return routes;
    }

    public List<Position> betweenSameCol(Position other) {
        List<Position> routes = new ArrayList<>();
        Row start = other.row.min(this.row);
        Row dest = other.row.max(this.row);
        for (Row row = start.next(); row.isLessThan(dest); row = row.next()) {
            routes.add(new Position(row, other.column));
        }
        return routes;
    }

    public Position add(Movement movement) {
        return new Position(this.row.add(movement.row()), this.column.add(movement.col()));
    }

    public Position middlePosition(Position other) {
        return new Position(this.row.divide(other.row), this.column.divide(other.column));
    }

    public int getRow() {
        return row.getValue();
    }

    public int getColumn() {
        return column.getValue();
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
