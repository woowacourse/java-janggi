package janggi.position;

public record Column(int value) {
    public Column move(int movement) {
        return new Column(value + movement);
    }

    public boolean isOutOfBounds() {
        return value < 1 || value > 9;
    }

    @Override
    public String toString() {
        return value + "";
    }
}
