package janggi.position;

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

    public Position(int y, int x) {
        validate(y, x);
        this.y = y;
        this.x = x;
    }

    public static Position valueOf(final int y, final int x) {
        return POSITIONS.get((y - 1) * 9 + x - 1);
    }

    public static Position from(final int value) {
        validate(value);
        int y = value / 10;
        int x = value % 10;
        return POSITIONS.get((y - 1) * 9 + x - 1);
    }

    private static void validate(int value) {
        if ((value < 11 || value > 109) || value % 10 == 0) {
            throw new IllegalArgumentException("[ERROR] 좌표는 장기판에 지정된 값만 입력할 수 있습니다.");
        }
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

    private static void validate(int y, int x) {
        if (y < Y_MIN_THRESHOLD || y > Y_MAX_THRESHOLD || x < X_MIN_THRESHOLD || x > X_MAX_THRESHOLD) {
            throw new IllegalArgumentException("[ERROR] y좌표는 1 이상 10이하, x좌표는 1이상 9이하여야 합니다.");
        }
    }

    public int calculateDifferenceForX(Position position) {
        return this.x - position.x;
    }

    public int calculateDifferenceForY(Position position) {
        return this.y - position.y;
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

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
