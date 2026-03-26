package domain;

import java.util.Objects;

public final class Position {
    private final int row;
    private final int column;

    private Position(int row, int column){
        this.row = row;
        this.column = column;
    }

    public static Position from(int row, int column) {
        return new Position(row, column);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
