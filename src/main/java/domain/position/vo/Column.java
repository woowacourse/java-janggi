package domain.position.vo;

public record Column(int value) {
    public static final int MIN_COL = 0;
    public static final int MAX_COL = 8;

    public Column {
        if (!isValid(value)) {
            throw new IllegalArgumentException(String.format("열은 %d ~ %d 값만 가능합니다.", MIN_COL, MAX_COL));
        }
    }

    private boolean isValid(final int value) {
        return value >= MIN_COL && value <= MAX_COL;
    }

    public boolean canMove(final int dc) {
        return isValid(value + dc);
    }
}
