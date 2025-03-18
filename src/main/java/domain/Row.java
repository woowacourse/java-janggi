package domain;

public class Row {
    private static final int MAX_ROW = 9;
    private static final int MIN_ROW = 0;
    private final int row;

    public Row(int row) {
        validateRow(row);
        this.row = row;
    }

    private void validateRow(int row) {
        if (row > MAX_ROW || row < MIN_ROW) {
            throw new IllegalArgumentException("장기판 행의 범위를 벗어났습니다.");
        }
    }
}
