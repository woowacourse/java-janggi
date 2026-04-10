package janggi.domain.move;

import janggi.domain.space.Direction;
import java.util.List;

public class MovePattern {
    public static final List<Direction> LINEAR = List.of(
            Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST
    );

    public static final List<Direction> CHO_SOLDIER = List.of(
            Direction.NORTH, Direction.EAST, Direction.WEST
    );

    public static final List<Direction> HAN_SOLDIER = List.of(
            Direction.SOUTH, Direction.EAST, Direction.WEST
    );

    public static final List<List<Direction>> HORSE = List.of(
            List.of(Direction.NORTH, Direction.NORTH_WEST), List.of(Direction.NORTH, Direction.NORTH_EAST),
            List.of(Direction.SOUTH, Direction.SOUTH_WEST), List.of(Direction.SOUTH, Direction.SOUTH_EAST),
            List.of(Direction.EAST, Direction.NORTH_EAST), List.of(Direction.EAST, Direction.SOUTH_EAST),
            List.of(Direction.WEST, Direction.NORTH_WEST), List.of(Direction.WEST, Direction.SOUTH_WEST)
    );

    public static final List<List<Direction>> ELEPHANT = List.of(
            List.of(Direction.NORTH, Direction.NORTH_WEST, Direction.NORTH_WEST), List.of(Direction.NORTH, Direction.NORTH_EAST, Direction.NORTH_EAST),
            List.of(Direction.SOUTH, Direction.SOUTH_WEST, Direction.SOUTH_WEST), List.of(Direction.SOUTH, Direction.SOUTH_EAST, Direction.SOUTH_EAST),
            List.of(Direction.EAST, Direction.NORTH_EAST, Direction.NORTH_EAST), List.of(Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH_EAST),
            List.of(Direction.WEST, Direction.NORTH_WEST, Direction.NORTH_WEST), List.of(Direction.WEST, Direction.SOUTH_WEST, Direction.SOUTH_WEST)
    );
}
