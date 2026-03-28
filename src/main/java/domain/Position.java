package domain;

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

    public static Position from(String row, String column) {
        try {
            return new Position(Integer.parseInt(row), Integer.parseInt(column));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("위치는 숫자로 입력해주세요.");
        }
    }

    public Position(Row row, Column column) {
        this.row = row;
        this.column = column;
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

    public List<Position> makeColStraightRoute(Position other) {
        List<Position> routes = new ArrayList<>();
        int start = other.column.min(this.column);
        int dest = other.column.max(this.column);
        for (int i = start + 1; i <= dest; i++) {
            routes.add(new Position(other.row, new Column(i)));
        }
        return routes;
    }

    public List<Position> makeRowStraightRoute(Position other) {
        List<Position> routes = new ArrayList<>();
        int start = other.row.min(this.row);
        int dest = other.row.max(this.row);
        for (int i = start + 1; i <= dest; i++) {
            routes.add(new Position(new Row(i), other.column));
        }
        return routes;
    }

    public Position addPosition(int x, int y) {
        return new Position(this.row.add(x), this.column.add(y));
    }

    public Position middlePosition(Position other) {
        return new Position(this.row.divide(other.row), this.column.divide(other.column));
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
