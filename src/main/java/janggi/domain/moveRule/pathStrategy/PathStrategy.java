package janggi.domain.moveRule.pathStrategy;

import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.piece.TeamColor;
import java.util.List;

public interface PathStrategy {
    boolean isValidMovement(PiecePath path, TeamColor teamColor);

    List<Position> findAllRoute(PiecePath path);
}
