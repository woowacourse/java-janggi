package domain.game;

import domain.strategy.Direction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Position {
    private static final Map<Integer, Position> CACHE = new HashMap<>();

    public static final int MIN = 0;
    public static final int MAX_X = 8;
    public static final int MAX_Y = 9;

    private final int x;
    private final int y;

    static {
        for (int x = MIN; x <= MAX_X; x++) {
            for (int y = MIN; y <= MAX_Y; y++) {
                CACHE.put(generateKey(x, y), new Position(x, y));
            }
        }
    }

    private Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position of(int x, int y) {
        validateRange(x, y);
        return CACHE.get(generateKey(x, y));
    }

    private static void validateRange(int x, int y) {
        if (!isWithinRange(x, y)) {
            throw new IllegalArgumentException(String.format("범위를 벗어난 좌표입니다: (%d, %d)", x, y));
        }
    }

    public boolean canMove(Direction direction) {
        return isWithinRange(this.x + direction.getDx(), this.y + direction.getDy());
    }

    public Position move(Direction direction) {
        return Position.of(this.x + direction.getDx(), this.y + direction.getDy());
    }

    public static boolean isWithinRange(int x, int y) {
        return x >= MIN && x <= MAX_X && y >= MIN && y <= MAX_Y;
    }

    private static int generateKey(int x, int y) {
        return x * 31 + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position position)) return false;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public List<Integer> getPosition() {
        return List.of(x, y);
    }
}
