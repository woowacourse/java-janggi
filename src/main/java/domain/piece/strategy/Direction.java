package domain.piece.strategy;

import domain.position.Position;
import java.util.Arrays;
import java.util.Optional;

public enum Direction {
    LEFT(0, -1),
    RIGHT(0, 1),
    UP(-1, 0),
    DOWN(1, 0),
    LEFT_UP(-1, -1),
    LEFT_DOWN(1, -1),
    RIGHT_UP(-1, 1),
    RIGHT_DOWN(1, 1);
    public static final String CAN_NOT_FIND_DIRECTION = "적절한 방향을 찾을 수 없습니다.";
    private final int dRow;
    private final int dColumn;

    Direction(int dRow, int dColumn) {
        this.dRow = dRow;
        this.dColumn = dColumn;
    }

    public static Direction getDirectionByPosition(Position from, Position to) {
        int dRow = Integer.compare(to.getRow().getValue(), from.getRow().getValue());
        int dColumn = Integer.compare(to.getColumn().getValue(), from.getColumn().getValue());

        Optional<Direction> OptDirection = Arrays.stream(values())
                .filter(direction -> direction.dRow == dRow && direction.dColumn == dColumn)
                .findFirst();
        if (OptDirection.isEmpty()) {
            throw new IllegalArgumentException(CAN_NOT_FIND_DIRECTION);
        }
        return OptDirection.get();
    }

    public boolean isMovable(Position start) {
        try {
            start.go(dRow, dColumn);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    public Position getMovedPosition(Position start) {
        return start.go(dRow, dColumn);
    }

    public boolean isDiagonal() {
        return this == LEFT_UP || this == LEFT_DOWN || this == RIGHT_UP || this == RIGHT_DOWN;
    }
}
