package janggi.domain.space;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return isWithinRange(this.x + direction.getDeltaX(), this.y + direction.getDeltaY());
    }

    public boolean canMove(List<Direction> sequence) {
        if (sequence.isEmpty()) {
            return true;
        }

        Direction direction = sequence.getFirst();
        if (!canMove(direction)) {
            return false;
        }

        return move(direction).canMove(sequence.subList(1, sequence.size()));
    }

    public Position move(Direction direction) {
        return Position.of(this.x + direction.getDeltaX(), this.y + direction.getDeltaY());
    }

    public static boolean isWithinRange(int x, int y) {
        return x >= MIN && x <= MAX_X && y >= MIN && y <= MAX_Y;
    }

    private static int generateKey(int x, int y) {
        return x * 10 + y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
