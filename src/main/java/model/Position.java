package model;

import java.util.Objects;

public class Position {

    public static final int MAXIMUM_COLUMN_VALUE = 9;
    public static final int MAXIMUM_ROW_VALUE = 8;
    private final int column;
    private final int row;

    public Position(int column, int row) {
        validate(column, row);
        this.column = column;
        this.row = row;
    }

    private void validate(int column, int row) {
        if ((0 > column || column > MAXIMUM_COLUMN_VALUE) || (0 > row || row > MAXIMUM_ROW_VALUE)) {
            throw new IllegalArgumentException("잘못된 이동입니다.");
        }
    }

    public Position changeColumnAndRow(int moveColumnAmount, int moveRowAmount) {
        return new Position(this.column + moveColumnAmount, this.row + moveRowAmount);
    }

    public Position changeColumn(int moveColumnAmount) {
        return new Position(this.column + moveColumnAmount, this.row);
    }

    public Position changeRow(int moveRowAmount) {
        return new Position(this.column, this.row + moveRowAmount);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return column == position.column && row == position.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }
}
