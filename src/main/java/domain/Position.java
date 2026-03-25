package domain;

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
}
