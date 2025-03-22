package piece;

import java.util.ArrayList;
import java.util.List;
import position.Path;
import position.Position;

public enum PieceType {
    GENERAL("장",
            List.of(MoveDirection.UP),
            List.of(MoveDirection.DOWN),
            List.of(MoveDirection.LEFT),
            List.of(MoveDirection.RIGHT)
    ),
    GUARD("사",
            List.of(MoveDirection.UP),
            List.of(MoveDirection.DOWN),
            List.of(MoveDirection.LEFT),
            List.of(MoveDirection.RIGHT)
    ),
    ROOK("차",
            List.of(MoveDirection.CROSS_INF)
    ),
    HORSE("마",
            List.of(MoveDirection.DOWN, MoveDirection.DOWN_RIGHT),
            List.of(MoveDirection.DOWN, MoveDirection.DOWN_LEFT),
            List.of(MoveDirection.UP, MoveDirection.UP_RIGHT),
            List.of(MoveDirection.UP, MoveDirection.UP_LEFT),
            List.of(MoveDirection.LEFT, MoveDirection.DOWN_LEFT),
            List.of(MoveDirection.LEFT, MoveDirection.UP_LEFT),
            List.of(MoveDirection.RIGHT, MoveDirection.DOWN_RIGHT),
            List.of(MoveDirection.RIGHT, MoveDirection.UP_RIGHT)
    ),
    ELEPHANT("상",
            List.of(MoveDirection.DOWN, MoveDirection.DOWN_RIGHT, MoveDirection.DOWN_RIGHT),
            List.of(MoveDirection.DOWN, MoveDirection.DOWN_LEFT, MoveDirection.DOWN_LEFT),
            List.of(MoveDirection.UP, MoveDirection.UP_RIGHT, MoveDirection.UP_RIGHT),
            List.of(MoveDirection.UP, MoveDirection.UP_LEFT, MoveDirection.UP_LEFT),
            List.of(MoveDirection.LEFT, MoveDirection.DOWN_LEFT, MoveDirection.DOWN_LEFT),
            List.of(MoveDirection.LEFT, MoveDirection.UP_LEFT, MoveDirection.UP_LEFT),
            List.of(MoveDirection.RIGHT, MoveDirection.DOWN_RIGHT, MoveDirection.DOWN_RIGHT),
            List.of(MoveDirection.RIGHT, MoveDirection.UP_RIGHT, MoveDirection.UP_RIGHT)
    ),
    CANNON("포",
            List.of(MoveDirection.CROSS_INF)
    ),
    CHO_SOLDIER("졸",
            List.of(MoveDirection.UP),
            List.of(MoveDirection.LEFT),
            List.of(MoveDirection.RIGHT)
    ),
    HAN_SOLDIER("병",
            List.of(MoveDirection.DOWN),
            List.of(MoveDirection.LEFT),
            List.of(MoveDirection.RIGHT)
    );

    private final String displayName;
    private final List<MoveDirection>[] moveOptions;

    PieceType(final String displayName, final List<MoveDirection>... moveOptions) {
        this.displayName = displayName;
        this.moveOptions = moveOptions;
    }

    public List<Path> getMoveablePaths(final Position currentPosition) {
        List<Path> paths = new ArrayList<>();
        for (List<MoveDirection> moveDirections : moveOptions) {
            paths.addAll(Path.getMoveablePaths(currentPosition, moveDirections));
        }
        return paths;
    }

    public String getDisplayName() {
        return displayName;
    }
}
