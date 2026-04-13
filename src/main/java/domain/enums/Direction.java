package domain.enums;

import java.util.List;

public enum Direction {
    UP(1, 0),
    DOWN(-1, 0),
    LEFT(0, 1),
    RIGHT(0, -1),

    UP_LEFT(1, 1),
    UP_RIGHT(1, -1),
    DOWN_LEFT(-1, 1),
    DOWN_RIGHT(-1, -1),
    LEFT_UP(1, 1),
    LEFT_DOWN(-1, 1),
    RIGHT_UP(-1, -1),
    RIGHT_DOWN(1, -1);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static List<Direction> getCardinalDirections(){
        return List.of(UP,DOWN,LEFT,RIGHT);
    }

    public static List<Direction> getDiagonalDirections() {
        return List.of(UP_LEFT,UP_RIGHT,DOWN_LEFT,DOWN_RIGHT);
    }

    public List<Direction> getMaSangDiagonalDirections(Direction direction){
        if (direction==Direction.UP){
            return List.of(UP_LEFT, UP_RIGHT);
        }
        if (direction==Direction.DOWN){
            return List.of(DOWN_LEFT, DOWN_RIGHT);
        }
        if (direction==Direction.LEFT){
            return List.of(LEFT_UP, LEFT_DOWN);
        }
        if (direction==Direction.RIGHT){
            return List.of(RIGHT_UP, RIGHT_DOWN);
        }
        throw new IllegalArgumentException("Invalid direction");
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
