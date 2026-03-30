package domain.position;

import java.util.Objects;

public class Position {

    private final int rows; // 행 10
    private final int columns; //열 9

    public Position(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return rows == position.rows && columns == position.columns;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rows, columns);
    }
}
