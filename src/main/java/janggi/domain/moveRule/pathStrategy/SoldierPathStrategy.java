package janggi.domain.moveRule.pathStrategy;

import janggi.domain.board.Direction;
import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.piece.TeamColor;
import java.util.List;

public class SoldierPathStrategy implements PathStrategy {
    private static final PathStrategy INSTANCE = new SoldierPathStrategy();

    private SoldierPathStrategy() {}

    public static PathStrategy getInstance() {
        return INSTANCE;
    }

    private final static List<Direction> SOLDIER_DIRECTION =
            List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT);
    private final static List<Direction> IN_PALACE_DIRECTION =
            List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT,
                    Direction.DOWN_LEFT, Direction.DOWN_RIGHT, Direction.UP_LEFT, Direction.UP_RIGHT);

    @Override
    public boolean isValidMovement(PiecePath path, TeamColor teamColor) {
        Direction direction = path.calculateDirection();

        if(isBackMovement(direction, teamColor)) {
            return false;
        }
        if(path.isInPalacePath() && IN_PALACE_DIRECTION.contains(direction)) {
            return true;
        }
        return SOLDIER_DIRECTION.contains(direction);
    }

    private boolean isBackMovement(Direction direction, TeamColor color) {
        if(color == TeamColor.BLUE && direction.getX() > 0) {
            return true;
        }
        if(color == TeamColor.RED && direction.getX() < 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<Position> findAllRoute(PiecePath path) {
        return List.of();
    }
}
