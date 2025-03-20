package domain.piece;

import domain.position.Path;
import domain.position.Position;

import java.util.ArrayList;
import java.util.List;

public enum PieceType {
    장(
            List.of(MoveDirection.UP),
            List.of(MoveDirection.DOWN),
            List.of(MoveDirection.LEFT),
            List.of(MoveDirection.RIGHT)
    ),
    사(
            List.of(MoveDirection.UP),
            List.of(MoveDirection.DOWN),
            List.of(MoveDirection.LEFT),
            List.of(MoveDirection.RIGHT)
    ),
    차(
            List.of(MoveDirection.CROSS_INF)
    ),
    마(
            List.of(MoveDirection.DOWN, MoveDirection.DOWN_RIGHT),
            List.of(MoveDirection.DOWN, MoveDirection.DOWN_LEFT),
            List.of(MoveDirection.UP, MoveDirection.UP_RIGHT),
            List.of(MoveDirection.UP, MoveDirection.UP_LEFT),
            List.of(MoveDirection.LEFT, MoveDirection.DOWN_LEFT),
            List.of(MoveDirection.LEFT, MoveDirection.UP_LEFT),
            List.of(MoveDirection.RIGHT, MoveDirection.DOWN_RIGHT),
            List.of(MoveDirection.RIGHT, MoveDirection.UP_RIGHT)
    ),
    상(
            List.of(MoveDirection.DOWN, MoveDirection.DOWN_RIGHT, MoveDirection.DOWN_RIGHT),
            List.of(MoveDirection.DOWN, MoveDirection.DOWN_LEFT, MoveDirection.DOWN_LEFT),
            List.of(MoveDirection.UP, MoveDirection.UP_RIGHT, MoveDirection.UP_RIGHT),
            List.of(MoveDirection.UP, MoveDirection.UP_LEFT, MoveDirection.UP_LEFT),
            List.of(MoveDirection.LEFT, MoveDirection.DOWN_LEFT, MoveDirection.DOWN_LEFT),
            List.of(MoveDirection.LEFT, MoveDirection.UP_LEFT, MoveDirection.UP_LEFT),
            List.of(MoveDirection.RIGHT, MoveDirection.DOWN_RIGHT, MoveDirection.DOWN_RIGHT),
            List.of(MoveDirection.RIGHT, MoveDirection.UP_RIGHT, MoveDirection.UP_RIGHT)
    ),
    포(
            List.of(MoveDirection.CROSS_INF)
    ),
    졸(
            List.of(MoveDirection.UP),
            List.of(MoveDirection.LEFT),
            List.of(MoveDirection.RIGHT)
    ),
    병(
            List.of(MoveDirection.DOWN),
            List.of(MoveDirection.LEFT),
            List.of(MoveDirection.RIGHT)
    ),
    ;

    private final List<MoveDirection>[] moveOptions;

    PieceType(final List<MoveDirection>... moveOptions) {
        this.moveOptions = moveOptions;
    }

    public List<Path> getMoveablePaths(final Position currentPosition) {
        List<Path> paths = new ArrayList<>();
        for (List<MoveDirection> moveDirections : moveOptions) {
            paths.addAll(currentPosition.getPathsFrom(moveDirections));
        }
        return paths;
    }
}
