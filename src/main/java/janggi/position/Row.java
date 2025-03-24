package janggi.position;

public record Row(int value) {
    public Row move(int row) {
        return new Row(value + row);
    }

    public boolean isOutOfBounds() {
        return value < 1 || value > 10;
    }

    @Override
    public String toString() {
        return value + "";
    }
}
