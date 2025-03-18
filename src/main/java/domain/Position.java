package domain;

public class Position {
    private final Row row;
    private final Column col;

    public Position(int row, int col) {
        this.row = new Row(row);
        this.col = new Column(col);
    }
}
