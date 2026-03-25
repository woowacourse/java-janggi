package janggi;

import java.util.List;

public record Position(
        Row row,
        Column column
) {

    public Path moveHorizontal(Position to) {
        if (!this.row.equals(to.row)) {
            throw new IllegalArgumentException("같은 행이 아닙니다.");
        }
        List<Column> columns = this.column.to(to.column);
        return new Path(columns.stream().map(column -> new Position(this.row, column)).toList());
    }

    public Path moveVertical(Position to) {
        if (!this.column.equals(to.column)) {
            throw new IllegalArgumentException("같은 열이 아닙니다.");
        }
        List<Row> rows = this.row.to(to.row);
        return new Path(rows.stream().map(row -> new Position(row, this.column)).toList());
    }

    public int getRowDistance(Position other) {
        return this.row.getDistance(other.row);
    }

    public int getColumnDistance(Position other) {
        return this.column.getDistance(other.column);
    }

    public Position moveEast() {
        return new Position(row, column.next());
    }

    public Position moveWest() {
        return new Position(row, column.previous());
    }


    public Position moveSouth() {
        return new Position(row.next(), column);
    }


    public Position moveNorth() {
        return new Position(row.previous(), column);
    }

    public Position moveNorthAndWest() {
        return new Position(row.previous(), column.previous());
    }

    public Position moveNorthAndEast() {
        return new Position(row.previous(), column.next());
    }

    public Position moveSouthAndEast() {
        return new Position(row.next(), column.next());
    }

    public Position moveSouthAndWest() {
        return new Position(row.next(), column.previous());
    }

    public boolean isSameRow(Position other) {
        return this.row == other.row;
    }

    public boolean isSameColumn(Position other) {
        return this.column == other.column;
    }
}
