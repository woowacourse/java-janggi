package janggi.domain;

import java.util.Objects;

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

    public static boolean isInsideBoundary(int x, int y) {
        return x >= MIN_X && x <= MAX_X && y >= MIN_Y && y <= MAX_Y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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

    private void validateBoundary(int x, int y) {
        if (x < MIN_X || x > MAX_X || y < MIN_Y || y > MAX_Y) {
            throw new IllegalArgumentException("[ERROR] 보드 범위를 벗어났습니다.");
        }
    }
}
