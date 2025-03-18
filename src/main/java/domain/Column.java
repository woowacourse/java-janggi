package domain;

public class Column {
    private static final int MAX_COLUMN = 8;
    private static final int MIN_COLUMN = 0;

    private final int column;

    public Column(int column) {
        validateColumn(column);
        this.column = column;
    }

    private void validateColumn(int column) {
        if (MAX_COLUMN < column || column < MIN_COLUMN) {
            throw new IllegalArgumentException("장기판 열의 범위를 벗어났습니다.");
        }
    }


}
