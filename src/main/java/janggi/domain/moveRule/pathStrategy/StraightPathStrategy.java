package janggi.domain.moveRule.pathStrategy;

import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.piece.TeamColor;
import java.util.List;

public class StraightPathStrategy implements PathStrategy {
    private static final PathStrategy INSTANCE = new StraightPathStrategy();

    private StraightPathStrategy() {}

    public static PathStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isValidMovement(PiecePath path, TeamColor teamColor) {
        if (path.isDiagonal()) {
            return path.isPalaceDiagonalLine();
        }
        return path.isStraight();
    }

    @Override
    public List<Position> findAllRoute(PiecePath path) {
        return path.getBetweenPositions();
    }
}
