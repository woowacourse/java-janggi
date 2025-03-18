package model;

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
        if ((0 < column || column > MAXIMUM_COLUMN_VALUE) || (0 < row || row > MAXIMUM_ROW_VALUE)) {
            throw new IllegalArgumentException("잘못된 이동입니다.");
        }
    }
}
