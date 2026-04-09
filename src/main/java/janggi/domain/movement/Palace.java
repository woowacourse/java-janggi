package janggi.domain.movement;

import static janggi.domain.movement.Direction.DOWN;
import static janggi.domain.movement.Direction.DOWN_LEFT;
import static janggi.domain.movement.Direction.DOWN_RIGHT;
import static janggi.domain.movement.Direction.LEFT;
import static janggi.domain.movement.Direction.RIGHT;
import static janggi.domain.movement.Direction.UP;
import static janggi.domain.movement.Direction.UP_LEFT;
import static janggi.domain.movement.Direction.UP_RIGHT;

import janggi.domain.Position;
import java.util.List;
import java.util.Map;

public final class Palace {
    private static final Map<Position, List<Direction>> PALACE_DIAGONAL_DIRECTIONS;

    static {
        PALACE_DIAGONAL_DIRECTIONS = Map.ofEntries(
                Map.entry(Position.valueOf(1, 4), List.of(DOWN_RIGHT, RIGHT, DOWN)),
                Map.entry(Position.valueOf(1, 5), List.of(DOWN, LEFT, RIGHT)),
                Map.entry(Position.valueOf(1, 6), List.of(DOWN_LEFT, DOWN, LEFT)),
                Map.entry(Position.valueOf(2, 4), List.of(UP, DOWN, RIGHT)),
                Map.entry(Position.valueOf(2, 5),
                        List.of(UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT, UP, DOWN, RIGHT, LEFT)),
                Map.entry(Position.valueOf(2, 6), List.of(UP, DOWN, LEFT)),
                Map.entry(Position.valueOf(3, 4), List.of(UP, UP_RIGHT, RIGHT)),
                Map.entry(Position.valueOf(3, 5), List.of(UP, LEFT, RIGHT)),
                Map.entry(Position.valueOf(3, 6), List.of(UP, UP_LEFT, LEFT)),

                Map.entry(Position.valueOf(8, 4), List.of(DOWN_RIGHT, RIGHT, DOWN)),
                Map.entry(Position.valueOf(8, 5), List.of(DOWN, LEFT, RIGHT)),
                Map.entry(Position.valueOf(8, 6), List.of(DOWN_LEFT, DOWN, LEFT)),
                Map.entry(Position.valueOf(9, 4), List.of(UP, DOWN, RIGHT)),
                Map.entry(Position.valueOf(9, 5),
                        List.of(UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT, UP, DOWN, RIGHT, LEFT)),
                Map.entry(Position.valueOf(9, 6), List.of(UP, DOWN, LEFT)),
                Map.entry(Position.valueOf(10, 4), List.of(UP, UP_RIGHT, RIGHT)),
                Map.entry(Position.valueOf(10, 5), List.of(UP, LEFT, RIGHT)),
                Map.entry(Position.valueOf(10, 6), List.of(UP, UP_LEFT, LEFT))
        );
    }

    private Palace() {
    }

    public static boolean isAllowedDirection(Position position, Direction direction) {
        if (PALACE_DIAGONAL_DIRECTIONS.containsKey(position)) {
            return isContainDirection(position, direction);
        }
        return false;
    }

    private static boolean isContainDirection(final Position position, final Direction direction) {
        return PALACE_DIAGONAL_DIRECTIONS.get(position).contains(direction);
    }
}
