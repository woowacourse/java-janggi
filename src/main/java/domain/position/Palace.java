package domain.position;

import domain.direction.Direction;

import java.util.Arrays;
import java.util.List;

public enum Palace {
    UP(Position.of(3, 5), List.of(Direction.LEFT, Direction.DOWN, Direction.RIGHT)),
    UP_LEFT(Position.of(3, 4), List.of(Direction.RIGHT, Direction.DOWN, Direction.DOWN_RIGHT)),
    UP_RIGHT(Position.of(3, 6), List.of(Direction.LEFT, Direction.DOWN, Direction.DOWN_LEFT)),
    CENTER(Position.of(2, 5), List.of(Direction.values())),
    DOWN(Position.of(1, 5), List.of(Direction.LEFT, Direction.UP, Direction.RIGHT)),
    DOWN_LEFT(Position.of(1, 4), List.of(Direction.UP, Direction.UP_RIGHT, Direction.RIGHT)),
    DOWN_RIGHT(Position.of(1, 6), List.of(Direction.LEFT, Direction.UP_LEFT, Direction.UP)),
    LEFT(Position.of(2, 4), List.of(Direction.UP, Direction.DOWN, Direction.RIGHT)),
    RIGHT(Position.of(2, 6), List.of(Direction.LEFT, Direction.DOWN, Direction.UP));

    private final Position position;
    private final List<Direction> moveAbleDirections;

    Palace(Position position, List<Direction> moveAbleDirections) {
        this.position = position;
        this.moveAbleDirections = moveAbleDirections;
    }

    public static boolean isPalace(Position position) {
        return Arrays.stream(values())
                .anyMatch(place -> place.position.equals(position));
    }

    public static boolean canMoveDiagonallyPosition(Position from, Direction direction) {
        return Arrays.stream(values())
                .anyMatch(place -> place.position.equals(from) && place.moveAbleDirections.contains(direction));
    }

    public static boolean canStepDiagonal(Position from, Direction direction) {
        return isPalace(from) && canMoveDiagonallyPosition(from, direction) && direction.isDiagonal();
    }
}
