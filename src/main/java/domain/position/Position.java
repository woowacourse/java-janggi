package domain.position;

import java.util.Objects;

public class Position {

    private final int row; // 행 10
    private final int column; //열 9

    public Position(int rows, int columns) {
        this.row = rows;
        this.column = columns;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
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
