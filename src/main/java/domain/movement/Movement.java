package domain.movement;

import domain.position.Position;

import java.util.Arrays;

public enum Movement {

    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    LEFT_UP(LEFT.x, UP.y),
    RIGHT_UP(RIGHT.x, UP.y),
    LEFT_DOWN(LEFT.x, DOWN.y),
    RIGHT_DOWN(RIGHT.x, DOWN.y)
    ;

    private final int x;

    private final int y;

    Movement(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public static Movement findVerticalByY(int y) {
        return switch (y) {
            case 1 -> UP;
            case -1 -> DOWN;
            default -> throw new IllegalArgumentException("존재할 수 없는 movement의 y값 입니다.");
        };
    }

    public static Movement findByPositions(Position position, Position destination) {
        int sigX = (int) Math.signum(destination.x() - position.x());
        int sigY = (int) Math.signum(destination.y() - position.y());
        return find(sigX, sigY);
    }

    public static Movement find(int x, int y) {
        return Arrays.stream(Movement.values())
                .filter(o -> o.x == x && o.y == y)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Movement 입니다."));
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }
}
