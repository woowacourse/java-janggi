package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.movement.Direction;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Palace {

    private static final Map<Position, List<Direction>> PALACE_DIRECTIONS_MAP;
    public static final int PALACE_SIDE_LENGTH = 2;

    static {
        PALACE_DIRECTIONS_MAP = new LinkedHashMap<>();
        PALACE_DIRECTIONS_MAP.putAll(redSidePalaceMap());
        PALACE_DIRECTIONS_MAP.putAll(blueSidePalaceMap());
    }

    private static Map<Position, List<Direction>> redSidePalaceMap() {
        return Map.of(
            Position.valueOf(1, 4), List.of(Direction.SOUTH_EAST),
            Position.valueOf(1, 5), List.of(),
            Position.valueOf(1, 6), List.of(Direction.SOUTH_WEST),
            Position.valueOf(2, 4), List.of(),
            Position.valueOf(2, 5), List.of(
                Direction.NORTH_WEST, Direction.NORTH_EAST, Direction.SOUTH_EAST,
                Direction.SOUTH_WEST),
            Position.valueOf(2, 6), List.of(),
            Position.valueOf(3, 4), List.of(Direction.NORTH_EAST),
            Position.valueOf(3, 5), List.of(),
            Position.valueOf(3, 6), List.of(Direction.NORTH_WEST));
    }

    private static Map<Position, List<Direction>> blueSidePalaceMap() {
        return Map.of(
            Position.valueOf(8, 4), List.of(Direction.SOUTH_EAST),
            Position.valueOf(8, 5), List.of(),
            Position.valueOf(8, 6), List.of(Direction.SOUTH_WEST),
            Position.valueOf(9, 4), List.of(),
            Position.valueOf(9, 5), List.of(
                Direction.NORTH_WEST, Direction.NORTH_EAST, Direction.SOUTH_EAST,
                Direction.SOUTH_WEST),
            Position.valueOf(9, 6), List.of(),
            Position.valueOf(10, 4), List.of(Direction.NORTH_EAST),
            Position.valueOf(10, 5), List.of(),
            Position.valueOf(10, 6), List.of(Direction.NORTH_WEST));
    }

    private Palace() {

    }

    public static boolean hasPosition(final Position position) {
        return PALACE_DIRECTIONS_MAP.containsKey(position);
    }

    public static boolean hasDirection(final Position position, final Direction direction) {
        if (!hasPosition(position)) {
            return false;
        }
        final List<Direction> movableDirections = PALACE_DIRECTIONS_MAP.get(position);

        return movableDirections.contains(direction);
    }

}
