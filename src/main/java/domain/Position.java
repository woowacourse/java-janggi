package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Position {
    public static final int INITIAL_POSITION = 0;
    public static final int X_MAXIMUM_POSITION = 8;
    public static final int Y_MAXIMUM_POSITION = 9;

    private final int x;
    private final int y;

    public Position(int x, int y) {
        validateRange(x, y);
        this.x = x;
        this.y = y;
    }

    private void validateRange(int x, int y) {
        if (isXInvalidRange(x) || isYInvalidRange(y)) {
            throw new IllegalArgumentException("[ERROR] x좌표와 y좌표의 범위가 올바르지 않습니다.");
        }
    }

    private boolean isXInvalidRange(int x) {
        return x < INITIAL_POSITION || x > X_MAXIMUM_POSITION;
    }

    private boolean isYInvalidRange(int y) {
        return y < INITIAL_POSITION || y > Y_MAXIMUM_POSITION;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Position position = (Position) object;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public List<Integer> calculateDistance(Position to) {
        List<Integer> distances = new ArrayList<>();
        distances.add(to.x - this.x);
        distances.add(to.y - this.y);
        return distances;
    }

    public Position nextPosition(Direction direction) {
        return new Position(x + direction.getX(), y + direction.getY());
    }
}
