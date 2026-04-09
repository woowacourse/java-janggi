package janggi.domain.moveRules;

import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.Team;
import java.util.List;
import java.util.Map;

public interface MoveRule {
    List<Position> calculateAvailablePositions(Position startPosition, Team team, Map<Position, Piece> state);
}
