package janggi.domain.position;

public record Position(
        Row row,
        Column column
) {

    public static Position from(int row, int column) {
        return new Position(new Row(row), new Column(column));
    }

    public Position add(int row, int column) {
        return new Position(this.row.add(row), this.column.add(column));
    }

}
