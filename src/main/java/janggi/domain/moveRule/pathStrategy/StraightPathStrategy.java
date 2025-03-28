package janggi.domain.moveRule.pathStrategy;

import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.piece.TeamColor;
import java.util.List;

public class StraightPathStrategy implements PathStrategy {
    @Override
    public boolean isValidMovement(PiecePath path, TeamColor teamColor) {
        if (path.isDiagonal()) {
            return path.isPalaceDiagonalLine();
        }
        return path.isStraight();
    }

    @Override
    public List<Position> findAllIntermediatePositions(PiecePath path) {
        return path.getBetweenPositions();
    }
}
