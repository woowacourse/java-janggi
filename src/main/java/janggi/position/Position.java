package janggi.position;

public class Position{
    private final Column column;
    private final Row row;

    public Position(Column column, Row row) {
        this.column = column;
        this.row = row;
    }

    public Position move(Row row, Column column) {
        return new Position(column.move(column), row.move(row));
    }

    public Position move(Position other) {
        return new Position(column.move(other.column), row.move(other.row));
    }

    public Position multiply(Position other) {
        // return new Position(x * other.x, y * other.y);
        return null;
    }
}
