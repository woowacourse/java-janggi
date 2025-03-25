package janggi.position;

public record Column(int value) {

    private static final int COLUMN_MIN = 1;
    private static final int COLUMN_MAX = 9;

    public Column move(int movement) {
        return new Column(value + movement);
    }

    public boolean isOutOfBounds() {
        return value < COLUMN_MIN || value > COLUMN_MAX;
    }

    @Override
    public String toString() {
        return value + "";
    }
}
