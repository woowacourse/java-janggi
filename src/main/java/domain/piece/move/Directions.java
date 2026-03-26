package domain.piece.move;

import java.util.List;

public enum Directions {

    UP_LEFT_UP(List.of(Direction.UP, Direction.LEFT_UP)),
    UP_LEFT_DOWN(List.of(Direction.UP, Direction.LEFT_DOWN)),
    DOWN_RIGHT_DOWN(List.of(Direction.DOWN, Direction.RIGHT_DOWN)),
    ;

    private final List<Direction> directions;

    Directions(List<Direction> directions) {
        this.directions = directions;
    }

    public List<Direction> directions() {
        return directions;
    }
}
