package janggi.domain.piece.moverules;

import janggi.domain.board.Board;
import janggi.domain.common.Position;
import janggi.domain.common.Team;
import janggi.domain.route.Route;
import java.util.List;

public interface MoveRule {
    List<Route> findRoutes(Team team);

    List<Position> findMovablePositions(Board board, Position position, Team team);
}
