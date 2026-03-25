package janggi;

import java.util.List;

public record Position(
        Row row,
        Column column
) {

    public Path moveNorthAndEast() {
        return new Path(List.of(this, new Position(row.previous(), column.next())));
    }

    public Path moveNorthAndWest() {
        return new Path(List.of(this, new Position(row.previous(), column.previous())));
    }

    public Path moveSouthAndEast() {
        return new Path(List.of(this, new Position(row.next(), column.next())));
    }

    public Path moveSouthAndWest() {
        return new Path(List.of(this, new Position(row.next(), column.previous())));
    }

    public Path moveDiagonal(Diagonal diagonal) {
        Row nextRow = row.next();
        Column nextColumn = column.next();

        if (diagonal.isNorth()) {
            nextRow = row.previous();
        }

        if (diagonal.isEast()) {
            nextColumn = column.previous();
        }

        return new Path(List.of(this, new Position(nextRow, nextColumn)));
    }

    public Path moveHorizontal(int distance) {
        Position to = new Position(
                row,
                Column.of(column.ordinal() + distance)
        );

        if (!this.row.equals(to.row)) {
            throw new IllegalArgumentException("같은 행이 아닙니다.");
        }
        List<Column> columns = this.column.to(to.column);
        return new Path(columns.stream().map(column -> new Position(this.row, column)).toList());
    }

    public Path moveVertical(int distance) {
        Position to = new Position(
                Row.of(row.ordinal() + distance),
                column
        );

        List<Row> rows = this.row.to(to.row);
        return new Path(rows.stream().map(row -> new Position(row, this.column)).toList());
    }


    public int getRowDistance(Position other) {
        return this.row.getDistance(other.row);
    }

    public int getColumnDistance(Position other) {
        return this.column.getDistance(other.column);
    }

    public boolean isSameRow(Position other) {
        return this.row == other.row;
    }

    public boolean isSameColumn(Position other) {
        return this.column == other.column;
    }
}
