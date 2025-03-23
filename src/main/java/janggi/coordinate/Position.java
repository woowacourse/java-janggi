package janggi.coordinate;

public record Position(Row row, Column column) {

    public static Position of(int row, int column) {
        return new Position(new Row(row), new Column(column));
    }

    public Position add(Vector delta) {
        return new Position(
                this.row.add(delta.deltaRow()),
                this.column.add(delta.deltaColumn())
        );
    }

    public Vector vectorTo(Position target) {
        return this.row.vectorTo(target.row)
                .add(this.column.vectorTo(target.column));
    }
}
