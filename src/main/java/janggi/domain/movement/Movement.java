package janggi.domain.movement;

import janggi.domain.position.Position;

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

    public static Movement from(final Position from, final Position to) {
        if (from.equals(to)) {
            throw new IllegalStateException("[ERROR] 같은 위치입니다.");
        }
        int columnDiff = to.getColumnValue() - from.getColumnValue();
        int rowDiff = to.getRowValue() - from.getRowValue();
        for (Movement movement : Movement.values()) {
            if (movement.columnValue == Integer.signum(columnDiff) && movement.rowValue == Integer.signum(rowDiff)) {
                return movement;
            }
        }
        throw new IllegalStateException("[ERROR] 직선 혹은 대각선 이동으로 갈 수 없는 위치입니다.");
    }

    public int columnValue() {
        return columnValue;
    }

    public int rowValue() {
        return rowValue;
    }
}
