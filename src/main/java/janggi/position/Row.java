package janggi.position;

public record Row(int value) {
    public Row move(int row) {
        return new Row(value + row);
    }

    public boolean isOutOfBounds() {
        return false;
    }

    @Override
    public String toString() {
        return value + "";
    }
}
