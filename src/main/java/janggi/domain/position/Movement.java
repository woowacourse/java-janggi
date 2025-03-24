package janggi.domain.position;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static janggi.domain.position.Direction.*;

public enum Movement {

    UP(List.of(Direction.UP)),
    DOWN((List.of(Direction.DOWN))),
    LEFT((List.of(Direction.LEFT))),
    RIGHT((List.of(Direction.RIGHT))),
    UP_UPLEFT((List.of(Direction.UP, UP_LEFT))),
    UP_UPRIGHT((List.of(Direction.UP, UP_RIGHT))),
    RIGHT_UPRIGHT((List.of(Direction.RIGHT, UP_RIGHT))),
    RIGHT_DOWNRIGHT((List.of(Direction.RIGHT, DOWN_RIGHT))),
    DOWN_DOWNLEFT((List.of(Direction.DOWN, DOWN_LEFT))),
    DOWN_DOWNRIGHT((List.of(DOWN_LEFT, DOWN_RIGHT))),
    LEFT_DOWNLEFT((List.of(Direction.LEFT, DOWN_LEFT))),
    LEFT_UPLEFT((List.of(Direction.LEFT, UP_LEFT))),
    UP_UPLEFT_UPLEFT((List.of(Direction.UP, UP_LEFT, UP_LEFT))),
    UP_UPRIGHT_UPRIGHT((List.of(Direction.UP, UP_RIGHT, UP_RIGHT))),
    RIGHT_UPRIGHT_UPRIGHT((List.of(Direction.RIGHT, UP_RIGHT, UP_RIGHT))),
    RIGHT_DOWNRIGHT_DOWNRIGHT((List.of(Direction.RIGHT, DOWN_RIGHT, DOWN_RIGHT))),
    DOWN_DOWNLEFT_DOWNLEFT((List.of(Direction.DOWN, DOWN_LEFT, DOWN_LEFT))),
    DOWN_DOWNRIGHT_DOWNRIGHT((List.of(Direction.DOWN, DOWN_RIGHT, DOWN_RIGHT))),
    LEFT_DOWNLEFT_DOWNLEFT((List.of(Direction.LEFT, DOWN_LEFT, DOWN_LEFT))),
    LEFT_UPLEFT_UPLEFT((List.of(Direction.LEFT, UP_LEFT, UP_LEFT))),
    ;

    private final List<Direction> directions;

    Movement(final List<Direction> directions) {
        this.directions = directions;
    }

    public List<Position> getPositionsWith(Position startPosition) {
        List<Position> positions = new ArrayList<>();
        positions.add(startPosition);
        for (Direction direction : directions) {
            final Optional<Position> nextPosition = direction.move(positions.getLast());
            if (nextPosition.isEmpty()) {
                return List.of();
            }
            positions.add(nextPosition.get());
        }
        return positions;
    }
}
