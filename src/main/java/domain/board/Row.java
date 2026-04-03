package domain.board;

public record Row(
        int value
) {

    private static final int TOPMOST = 1;
    private static final int BOTTOMMOST = 10;

    public static Row minimumRow() {
        return new Row(TOPMOST);
    }

    public boolean isOutOfBoard() {
        return value < TOPMOST || value > BOTTOMMOST;
    }

    public boolean isInBoard() {
        return !isOutOfBoard();
    }

    public boolean isDifferent(int value) {
        return this.value != value;
    }

    public Row nextRow() {
        return new Row(value + 1);
    }
}
