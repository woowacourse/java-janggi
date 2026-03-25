package janggi;

public record Position(
    Row row,
    Column column
) {
    public Position moveEast() {
        return new Position(row, column.next());
    }

    public Position moveWest() {
        return new Position(row, column.previous());
    }


    public Position moveSouth() {
        return new Position(row.next() , column);
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
