package janggi.domain.board;

import java.util.Arrays;
import java.util.EnumSet;

public enum PalacePosition {
    EAST(new Position(1, 3), EnumSet.noneOf(Direction.class)),
    WEST(new Position(1, 5), EnumSet.noneOf(Direction.class)),
    NORTH(new Position(0, 4), EnumSet.noneOf(Direction.class)),
    SOUTH(new Position(2, 4), EnumSet.noneOf(Direction.class)),
    CENTER(new Position(1, 4), EnumSet.of(Direction.NE, Direction.NW, Direction.SE, Direction.SW)),
    NORTH_EAST(new Position(0, 5), EnumSet.of(Direction.SW)),
    NORTH_WEST(new Position(0, 3), EnumSet.of(Direction.SE)),
    SOUTH_EAST(new Position(2, 5), EnumSet.of(Direction.NW)),
    SOUTH_WEST(new Position(2, 3), EnumSet.of(Direction.NE)),
    ;

    private final Position position;
    private final EnumSet<Direction> directions;

    PalacePosition(Position position, EnumSet<Direction> directions) {
        this.position = position;
        this.directions = directions;
    }

    public static EnumSet<Direction> palaceDirections(Position position) {
        PalacePosition target = Arrays.stream(values())
                .filter(palacePosition -> palacePosition.position.equals(position))
                .findAny()
                .orElse(EAST);
        System.out.println(target);
        return EnumSet.copyOf(target.directions);
    }
}
