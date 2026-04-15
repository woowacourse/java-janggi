package janggi.domain.common;

import janggi.domain.board.Palace;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Position {

    private static final int MIN_X = 1;
    private static final int MAX_X = 9;
    private static final int MIN_Y = 1;
    private static final int MAX_Y = 10;
    private final int x;
    private final int y;

    public Position(int x, int y) {
        validateBoundary(x, y);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Optional<Position> applyDirection(int dx, int dy) {
        if (isInsideBoundary(x + dx, y + dy)) {
            return Optional.of(new Position(x + dx, y + dy));
        }
        return Optional.empty();
    }

    public void applyContinuousDirection(int dx, int dy, Map<Position, List<Position>> continuousRoute) {
        List<Position> result = new ArrayList<>();
        int nextX = x + dx;
        int nextY = y + dy;

        boolean isDiagonal = (dx != 0 && dy != 0);
        while (isInsideBoundary(nextX, nextY) && (!isDiagonal || Palace.isInAnyPalace(new Position(nextX, nextY)))) {
            Position nextPosition = new Position(nextX, nextY);

            result.add(nextPosition);
            continuousRoute.put(nextPosition, new ArrayList<>(result));
            nextX += dx;
            nextY += dy;
        }
    }

    private void validateBoundary(int x, int y) {
        if (x < MIN_X || x > MAX_X || y < MIN_Y || y > MAX_Y) {
            throw new IllegalArgumentException("[ERROR] 보드 범위를 벗어났습니다.");
        }
    }

    private boolean isInsideBoundary(int x, int y) {
        return x >= MIN_X && x <= MAX_X && y >= MIN_Y && y <= MAX_Y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position position)) {
            return false;
        }
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
