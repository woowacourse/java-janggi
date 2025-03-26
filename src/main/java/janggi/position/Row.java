package janggi.position;

public record Row(int value) {

    private static final int ROW_MIN = 1;
    private static final int ROW_MAX = 10;

    public Row move(int row) {
        return new Row(value + row);
    }

    public boolean isOutOfBounds() {
        return value < ROW_MIN || value > ROW_MAX;
    }
}
