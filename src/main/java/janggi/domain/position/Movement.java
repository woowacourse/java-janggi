package janggi.domain.position;

import java.util.ArrayList;
import java.util.List;

public enum Movement {

    UP(List.of(Direction.UP)),
    DOWN(List.of(Direction.DOWN)),
    LEFT(List.of(Direction.LEFT)),
    RIGHT(List.of(Direction.RIGHT)),

    UP_LEFT(List.of(Direction.UP_LEFT)),
    UP_RIGHT(List.of(Direction.UP_RIGHT)),
    DOWN_LEFT(List.of(Direction.DOWN_LEFT)),
    DOWN_RIGHT(List.of(Direction.DOWN_RIGHT)),

    UP_UPLEFT(List.of(Direction.UP, Direction.UP_LEFT)),
    UP_UPRIGHT(List.of(Direction.UP, Direction.UP_RIGHT)),
    RIGHT_UPRIGHT(List.of(Direction.RIGHT, Direction.UP_RIGHT)),
    RIGHT_DOWNRIGHT(List.of(Direction.RIGHT, Direction.DOWN_RIGHT)),
    DOWN_DOWNLEFT(List.of(Direction.DOWN, Direction.DOWN_LEFT)),
    DOWN_DOWNRIGHT(List.of(Direction.DOWN, Direction.DOWN_RIGHT)),
    LEFT_DOWNLEFT(List.of(Direction.LEFT, Direction.DOWN_LEFT)),
    LEFT_UPLEFT(List.of(Direction.LEFT, Direction.UP_LEFT)),

    UP_UPLEFT_UPLEFT(List.of(Direction.UP, Direction.UP_LEFT, Direction.UP_LEFT)),
    UP_UPRIGHT_UPRIGHT(List.of(Direction.UP, Direction.UP_RIGHT, Direction.UP_RIGHT)),
    RIGHT_UPRIGHT_UPRIGHT(List.of(Direction.RIGHT, Direction.UP_RIGHT, Direction.UP_RIGHT)),
    RIGHT_DOWNRIGHT_DOWNRIGHT(List.of(Direction.RIGHT, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT)),
    DOWN_DOWNLEFT_DOWNLEFT(List.of(Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_LEFT)),
    DOWN_DOWNRIGHT_DOWNRIGHT(List.of(Direction.DOWN, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT)),
    LEFT_DOWNLEFT_DOWNLEFT(List.of(Direction.LEFT, Direction.DOWN_LEFT, Direction.DOWN_LEFT)),
    LEFT_UPLEFT_UPLEFT(List.of(Direction.LEFT, Direction.UP_LEFT, Direction.UP_LEFT)),
    ;

    private final List<Direction> directions;

    Movement(final List<Direction> directions) {
        this.directions = directions;
    }

    public List<Position> getPositionsWith(final Position startPosition) {
        final List<Position> positions = new ArrayList<>();
        positions.add(startPosition);
        for (Direction direction : directions) {
            final Position currentPosition = positions.getLast();
            if (!currentPosition.isValidToMove(direction)) {
                return List.of();
            }
            positions.add(currentPosition.move(direction));
        }
        return positions;
    }
}
