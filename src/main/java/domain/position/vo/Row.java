package domain.position.vo;

public record Row(int value) {
    public static final int MIN_ROW = 0;
    public static final int MAX_ROW = 9;

    public Row {
        if (!isValid(value)) {
            throw new IllegalArgumentException(String.format("행은 %d ~ %d 값만 가능합니다.", MIN_ROW, MAX_ROW));
        }
    }

    private boolean isValid(final int value) {
        return value >= MIN_ROW && value <= MAX_ROW;
    }

    public boolean canMove(final int dr) {
        return isValid(value + dr);
    }
}
