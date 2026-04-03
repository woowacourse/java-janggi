package domain.position;

public record Row(int row) {

    public static final int MIN_ROW = 1;
    public static final int MAX_ROW = 10;

    public Row {
        validateRange(row);
    }

    public boolean isOutBoundRow(int row) {
        return row < MIN_ROW || row > MAX_ROW;
    }

    private void validateRange(int row) {
        if (isOutBoundRow(row)) {
            throw new IllegalArgumentException("[ERROR] 좌표 범위를 초과했습니다.");
        }
    }

}
