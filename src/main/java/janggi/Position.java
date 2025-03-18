package janggi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Position {

    private static final int Y_MIN_THRESHOLD = 1;
    private static final int Y_MAX_THRESHOLD = 10;
    private static final int X_MIN_THRESHOLD = 1;
    private static final int X_MAX_THRESHOLD = 9;
    private static final List<Position> POSITIONS = initialize();
    private static final int MOVE_UNIT = 1;
    private final int y;
    private final int x;

    public Position(int y, int x) {
        validate(y, x);
        this.y = y;
        this.x = x;
    }

    private static List<Position> initialize() {
        List<Position> positions = new ArrayList<>();
        for (int i = Y_MIN_THRESHOLD; i <= Y_MAX_THRESHOLD; i++) {
            for (int j = X_MIN_THRESHOLD; j <= X_MAX_THRESHOLD; j++) {
                positions.add(new Position(i, j));
            }
        }
        return positions;
    }

    public Position moveForward() {
        return valueOf(y - MOVE_UNIT, x);
    }

    public Position moveLeft() {
        return valueOf(y, x - MOVE_UNIT);
    }

    public Position moveRight() {
        return valueOf(y, x + MOVE_UNIT);
    }

    public Position moveBackward() {
        return valueOf(y + MOVE_UNIT, x);
    }

    private void validate(int y, int x) {
        if (y < Y_MIN_THRESHOLD || y > Y_MAX_THRESHOLD || x < X_MIN_THRESHOLD || x > X_MAX_THRESHOLD) {
            throw new IllegalArgumentException("[ERROR] y좌표는 1 이상 10이하, x좌표는 1이상 9이하여야 합니다.");
        }
    }

    private Position valueOf(final int y, final int x) {
        return POSITIONS.get((y - 1) * 9 + x - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return y == position.y && x == position.x;
    }

    @Override
    public int hashCode() {
        return Objects.hash(y, x);
    }

    @Override
    public String toString() {
        return "Position{" +
                "y=" + y +
                ", x=" + x +
                '}';
    }
}
