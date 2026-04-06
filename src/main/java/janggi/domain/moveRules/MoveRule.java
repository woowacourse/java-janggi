package janggi.domain.moveRules;

import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Team;
import janggi.domain.board.Board;
import java.util.List;

public interface MoveRule {
    List<Route> findRoutes(Team team);

    List<Position> calculateAvailablePositions(Position position, Team team, Board board);
}
