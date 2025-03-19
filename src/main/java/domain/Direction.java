package domain;

public enum Direction {
    LEFT(-1, 0),
    RIGHT(1, 0),
    TOP(0, 1),
    BOTTOM(0, -1),
    ;

    private final int deltaRow;
    private final int deltaColumn;

    Direction(int deltaRow, int deltaColumn) {
        this.deltaRow = deltaRow;
        this.deltaColumn = deltaColumn;
    }

    public int getDeltaRow() {
        return deltaRow;
    }

    public int getDeltaColumn() {
        return deltaColumn;
    }
}
