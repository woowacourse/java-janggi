package janggi.domain.moveRule.pathStrategy;

import janggi.domain.board.Direction;
import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.piece.TeamColor;
import java.util.List;

public class PalacePathStrategy implements PathStrategy {
    private static final PathStrategy INSTANCE = new PalacePathStrategy();

    private static final List<Direction> IN_PALACE_DIRECTION =
            List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT,
                    Direction.DOWN_LEFT, Direction.DOWN_RIGHT, Direction.UP_LEFT, Direction.UP_RIGHT);

    private PalacePathStrategy() {}

    public static PathStrategy getInstance() {
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
