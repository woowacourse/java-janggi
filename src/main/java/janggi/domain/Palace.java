package janggi.domain;

import java.util.List;
import java.util.stream.Stream;

public enum Palace {
    UP(List.of(
            new Position(5, 3),
            new Position(5, 10)
    ),
            List.of(
                    new MovePath(List.of(Delta.right())),
                    new MovePath(List.of(Delta.left())),
                    new MovePath(List.of(Delta.down()))
            )
    ),
    DOWN(List.of(
            new Position(5, 1),
            new Position(5, 8)
    ),
            List.of(
                    new MovePath(List.of(Delta.right())),
                    new MovePath(List.of(Delta.left())),
                    new MovePath(List.of(Delta.up()))
            )
    ),
    RIGHT(List.of(
            new Position(6, 2),
            new Position(6, 9)
    ),
            List.of(
                    new MovePath(List.of(Delta.up())),
                    new MovePath(List.of(Delta.left())),
                    new MovePath(List.of(Delta.down())))
    ),
    LEFT(List.of(
            new Position(4, 2),
            new Position(4, 9)
    ),
            List.of(
                    new MovePath(List.of(Delta.up())),
                    new MovePath(List.of(Delta.right())),
                    new MovePath(List.of(Delta.down())))
    ),
    RIGHT_UP(List.of(
            new Position(6, 3),
            new Position(6, 10)
    ),
            List.of(
                    new MovePath(List.of(Delta.left())),
                    new MovePath(List.of(Delta.leftDown())),
                    new MovePath(List.of(Delta.down())))
    ),
    LEFT_UP(List.of(
            new Position(4, 3),
            new Position(4, 10)
    ),
            List.of(
                    new MovePath(List.of(Delta.right())),
                    new MovePath(List.of(Delta.rightDown())),
                    new MovePath(List.of(Delta.down())))
    ),
    RIGHT_DOWN(List.of(
            new Position(6, 1),
            new Position(6, 8)
    ),
            List.of(
                    new MovePath(List.of(Delta.left())),
                    new MovePath(List.of(Delta.leftUp())),
                    new MovePath(List.of(Delta.up())))
    ),
    LEFT_DOWN(List.of(
            new Position(4, 1),
            new Position(4, 8)
    ),
            List.of(
                    new MovePath(List.of(Delta.right())),
                    new MovePath(List.of(Delta.rightUp())),
                    new MovePath(List.of(Delta.up())))
    ),
    CENTER(List.of(
            new Position(5, 2),
            new Position(5, 9)
    ),
            List.of(
                    new MovePath(List.of(Delta.up())),
                    new MovePath(List.of(Delta.down())),
                    new MovePath(List.of(Delta.right())),
                    new MovePath(List.of(Delta.left())),
                    new MovePath(List.of(Delta.rightUp())),
                    new MovePath(List.of(Delta.rightDown())),
                    new MovePath(List.of(Delta.leftDown())),
                    new MovePath(List.of(Delta.leftUp())))
    )
    ;

    private final List<Position> positions;
    private final List<MovePath> additionalPaths;

    Palace(List<Position> positions, List<MovePath> additionalPaths) {
        this.positions = positions;
        this.additionalPaths = additionalPaths;
    }

    public static List<MovePath> findPossiblePaths(Position position) {
        for (Palace palace : values()) {
            if (palace.positions.contains(position)) {
                return palace.additionalPaths;
            }
        }
        return List.of();
    }

    public static boolean isCenter(Position start) {
        return CENTER.positions.contains(start);
    }

    public static boolean isCorner(Position start) {
        return getConerPositions().contains(start);
    }

    private static List<Position> getConerPositions() {
        return Stream.of(RIGHT_UP.positions, RIGHT_DOWN.positions, LEFT_UP.positions, LEFT_DOWN.positions)
                .flatMap(List::stream)
                .toList();
    }
}
