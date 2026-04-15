package janggi.domain.common;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public enum Direction {
    UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0),
    UP_LEFT(-1, -1), UP_RIGHT(1, -1), DOWN_LEFT(-1, 1), DOWN_RIGHT(1, 1);

    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Optional<Position> nextPosition(Position position) {
        return position.applyDirection(x, y);
    }

    public void nextContinuousPosition(Position position, Map<Position, List<Position>> continuousRoute) {
        position.applyContinuousDirection(x, y, continuousRoute);
    }

    public boolean isDownDiagonal() {
        return this == DOWN_LEFT || this == DOWN_RIGHT;
    }

    public boolean isUpDiagonal() {
        return this == UP_LEFT || this == UP_RIGHT;
    }
}
