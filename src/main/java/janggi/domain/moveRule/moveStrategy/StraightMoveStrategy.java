package janggi.domain.moveRule.moveStrategy;

import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.piece.TeamColor;
import java.util.List;

public class StraightMoveStrategy implements MoveStrategy {
    private static final MoveStrategy INSTANCE = new StraightMoveStrategy();

    private StraightMoveStrategy() {}

    public static MoveStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isValidMovement(PiecePath path, TeamColor teamColor) {
        if (path.isInPalacePath() && path.isDiagonal() && path.hasPalaceCenter()) {
            return true;
        }
        return path.isStraight();
    }

    @Override
    public List<Position> findAllRoute(PiecePath path) {
        return path.getBetweenPositions();
    }
}
