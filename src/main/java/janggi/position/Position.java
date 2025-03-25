package janggi.position;

import janggi.piece.direction.Direction;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Position {

    private static final int Y_MIN_THRESHOLD = 1;
    private static final int Y_MAX_THRESHOLD = 10;
    private static final int X_MIN_THRESHOLD = 1;
    private static final int X_MAX_THRESHOLD = 9;
    private static final List<Position> POSITIONS = initialize();

    private final int y;
    private final int x;

    public Position(final int y, final int x) {
        validate(y, x);
        this.y = y;
        this.x = x;
    }

    public Position(final Position input) {
        this(input.getY(), input.getX());
    }

    public static Position valueOf(final int y, final int x) {
        return POSITIONS.get((y - Y_MIN_THRESHOLD) * X_MAX_THRESHOLD + x - X_MIN_THRESHOLD);
    }

    public static Position from(final int value) {
        validate(value);
        final int y = value / Y_MAX_THRESHOLD;
        final int x = value % Y_MAX_THRESHOLD;
        return valueOf(y, x);
    }

    private static void validate(final int value) {
        if ((value <= Y_MAX_THRESHOLD || value >= (Y_MIN_THRESHOLD + Y_MAX_THRESHOLD) * Y_MAX_THRESHOLD)
                || value % Y_MAX_THRESHOLD == 0) {
            throw new IllegalArgumentException("[ERROR] 좌표는 장기판에 지정된 값만 입력할 수 있습니다.");
        }
    }

    private static List<Position> initialize() {
        final List<Position> positions = new ArrayList<>();
        for (int i = Y_MIN_THRESHOLD; i <= Y_MAX_THRESHOLD; i++) {
            for (int j = X_MIN_THRESHOLD; j <= X_MAX_THRESHOLD; j++) {
                positions.add(new Position(i, j));
            }
        }
        return positions;
    }

    private static void validate(final int y, final int x) {
        if (y < Y_MIN_THRESHOLD || y > Y_MAX_THRESHOLD || x < X_MIN_THRESHOLD || x > X_MAX_THRESHOLD) {
            throw new IllegalArgumentException("[ERROR] y좌표는 1 이상 10이하, x좌표는 1이상 9이하여야 합니다.");
        }
    }

    public int calculateDifferenceForX(final Position position) {
        return this.x - position.x;
    }

    public int calculateDifferenceForY(final Position position) {
        return this.y - position.y;
    }

    public Position move(final Direction direction) {
        return new Position(y + direction.getY(), x + direction.getX());
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Position position = (Position) o;
        return y == position.y && x == position.x;
    }

    @Override
    public int hashCode() {
        return Objects.hash(y, x);
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
