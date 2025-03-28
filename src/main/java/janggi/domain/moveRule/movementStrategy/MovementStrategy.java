package janggi.domain.moveRule.movementStrategy;

import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.piece.TeamColor;
import java.util.List;

public interface MovementStrategy {
    boolean isValidMovement(PiecePath path, TeamColor teamColor);

    List<Position> findAllIntermediatePositions(PiecePath path);
}
