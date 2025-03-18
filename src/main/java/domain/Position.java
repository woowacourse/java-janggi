package domain;

import java.util.Objects;

public class Position {
    private final Row row;
    private final Column col;

    public Position(int row, int col) {
        this.row = new Row(row);
        this.col = new Column(col);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return Objects.equals(row, position.row) && Objects.equals(col, position.col);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
