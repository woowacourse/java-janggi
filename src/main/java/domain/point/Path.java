package domain.point;

import static domain.point.Direction.DOWN;
import static domain.point.Direction.LEFT;
import static domain.point.Direction.RIGHT;
import static domain.point.Direction.UP;

import java.util.List;

public enum Path {

    UP_PATH(List.of(UP)),
    RIGHT_PATH(List.of(RIGHT)),
    DOWN_PATH(List.of(DOWN)),
    LEFT_PATH(List.of(LEFT)),
    UP_UP_LEFT_PATH(List.of(UP, UP, LEFT)),
    UP_UP_RIGHT_PATH(List.of(UP, UP, RIGHT)),
    RIGHT_RIGHT_UP_PATH(List.of(RIGHT, RIGHT, UP)),
    RIGHT_RIGHT_DOWN_PATH(List.of(RIGHT, RIGHT, DOWN)),
    DOWN_DOWN_RIGHT_PATH(List.of(DOWN, DOWN, RIGHT)),
    DOWN_DOWN_LEFT_PATH(List.of(DOWN, DOWN, LEFT)),
    LEFT_LEFT_DOWN_PATH(List.of(LEFT, LEFT, DOWN)),
    LEFT_LEFT_UP_PATH(List.of(LEFT, LEFT, UP)),
    UP_UP_UP_LEFT_LEFT_PATH(List.of(UP, UP, UP, LEFT, LEFT)),
    UP_UP_UP_RIGHT_RIGHT_PATH(List.of(UP, UP, UP, RIGHT, RIGHT)),
    RIGHT_RIGHT_RIGHT_UP_UP_PATH(List.of(RIGHT, RIGHT, RIGHT, UP, UP)),
    RIGHT_RIGHT_RIGHT_DOWN_DOWN_PATH(List.of(RIGHT, RIGHT, RIGHT, DOWN, DOWN)),
    DOWN_DOWN_DOWN_RIGHT_RIGHT_PATH(List.of(DOWN, DOWN, DOWN, RIGHT, RIGHT)),
    DOWN_DOWN_DOWN_LEFT_LEFT_PATH(List.of(DOWN, DOWN, DOWN, LEFT, LEFT)),
    LEFT_LEFT_LEFT_DOWN_DOWN_PATH(List.of(LEFT, LEFT, LEFT, DOWN, DOWN)),
    LEFT_LEFT_LEFT_UP_UP_PATH(List.of(LEFT, LEFT, LEFT, UP, UP)),
    ;

    private final List<Direction> directions;

    Path(List<Direction> directions) {
        this.directions = directions;
    }

    public List<Direction> directions() {
        return directions;
    }
}
