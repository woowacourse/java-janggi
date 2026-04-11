package domain.movement.vo;

public enum Direction {
    UP(0, -1),
    UP_RIGHT(1, -1),
    RIGHT(1, 0),
    DOWN_RIGHT(1, 1),
    DOWN(0, 1),
    DOWN_LEFT(-1, 1),
    LEFT(-1, 0),
    UP_LEFT(-1, -1),
    ;

    private final int columnDelta;
    private final int rowDelta;

    Direction(int colDelta, int rowDelta) {
        this.columnDelta = colDelta;
        this.rowDelta = rowDelta;
    }

    public Delta delta() {
        return new Delta(columnDelta, rowDelta);
    }
}
