package janggi.domain.moveRule.moveStrategy;

import janggi.domain.board.Direction;
import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.piece.TeamColor;
import java.util.List;

public class PalaceMoveStrategy implements MoveStrategy {
    private static final MoveStrategy INSTANCE = new PalaceMoveStrategy();

    private static final List<Direction> IN_PALACE_DIRECTION =
            List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT,
                    Direction.DOWN_LEFT, Direction.DOWN_RIGHT, Direction.UP_LEFT, Direction.UP_RIGHT);

    private PalaceMoveStrategy() {}

    public static MoveStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isValidMovement(PiecePath path, TeamColor teamColor) {
        if (!path.isInPalacePath()) {
            return false;
        }
        if(path.isDiagonal() && !path.hasPalaceCenter()) {
            return false;
        }
        return IN_PALACE_DIRECTION.stream()
                .anyMatch(path::canReachToDestination);
    }

    @Override
    public List<Position> findAllRoute(PiecePath path) {
        return List.of();
    }
}
