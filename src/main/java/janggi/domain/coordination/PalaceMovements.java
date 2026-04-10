package janggi.domain.coordination;

import janggi.domain.piece.path.Direction;
import janggi.domain.piece.path.Movement;
import janggi.domain.point.Point;
import java.util.List;

public enum PalaceMovements {
    NORTH(
            List.of(new Point(2, 4), new Point(9, 4)),
            List.of(new Movement(Direction.EAST),
                    new Movement(Direction.WEST),
                    new Movement(Direction.SOUTH))),
    NORTH_WEST(
            List.of(new Point(2, 3), new Point(9, 3)),
            List.of(new Movement(Direction.EAST),
                    new Movement(Direction.SOUTH),
                    new Movement(Direction.SOUTH_EAST))),
    WEST(
            List.of(new Point(1, 3), new Point(8, 3)),
            List.of(new Movement(Direction.EAST),
                    new Movement(Direction.SOUTH),
                    new Movement(Direction.NORTH))),
    SOUTH_WEST(
            List.of(new Point(0, 3), new Point(7, 3)),
            List.of(new Movement(Direction.NORTH_EAST),
                    new Movement(Direction.NORTH),
                    new Movement(Direction.EAST))),
    SOUTH(
            List.of(new Point(0, 4), new Point(7, 4)),
            List.of(new Movement(Direction.EAST),
                    new Movement(Direction.WEST),
                    new Movement(Direction.NORTH))),
    SOUTH_EAST(
            List.of(new Point(0, 5), new Point(7, 5)),
            List.of(new Movement(Direction.WEST),
                    new Movement(Direction.NORTH),
                    new Movement(Direction.NORTH_WEST))),
    EAST(
            List.of(new Point(1, 5), new Point(8, 5)),
            List.of(new Movement(Direction.WEST),
                    new Movement(Direction.SOUTH),
                    new Movement(Direction.NORTH))),
    NORTH_EAST(
            List.of(new Point(2, 5), new Point(9, 5)),
            List.of(new Movement(Direction.WEST),
                    new Movement(Direction.SOUTH),
                    new Movement(Direction.SOUTH_WEST))),
    CENTER(
            List.of(new Point(1, 4), new Point(8, 4)),
            List.of(new Movement(Direction.NORTH),
                    new Movement(Direction.SOUTH),
                    new Movement(Direction.WEST),
                    new Movement(Direction.EAST),
                    new Movement(Direction.NORTH_WEST),
                    new Movement(Direction.NORTH_EAST),
                    new Movement(Direction.SOUTH_WEST),
                    new Movement(Direction.SOUTH_EAST)
            ));
    private final List<Point> target;
    private final List<Movement> movements;

    PalaceMovements(List<Point> target, List<Movement> movements) {
        this.target = target;
        this.movements = movements;
    }

    public static List<Movement> getMovements(Point point) {
        for (PalaceMovements value : values()) {
            if (value.target.contains(point)) {
                return value.movements;
            }
        }
        throw new IllegalStateException("궁성에는 해당하는 Point가 없습니다. x:%d, y:%d".formatted(point.x(), point.y()));
    }
}
