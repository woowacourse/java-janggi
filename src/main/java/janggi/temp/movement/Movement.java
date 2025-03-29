package janggi.temp.movement;

public enum Movement {

    RIGHT(1, 0),
    LEFT(-1, 0),
    UP(0, -1),
    DOWN(0, 1),
    RIGHT_UP(1, -1),
    RIGHT_DOWN(1, 1),
    LEFT_UP(-1, -1),
    LEFT_DOWN(-1, 1);

    private final int columnValue;
    private final int rowValue;

    Movement(final int columnValue, final int rowValue) {
        this.columnValue = columnValue;
        this.rowValue = rowValue;
    }

    public int columnValue() {
        return columnValue;
    }

    public int rowValue() {
        return rowValue;
    }
}
