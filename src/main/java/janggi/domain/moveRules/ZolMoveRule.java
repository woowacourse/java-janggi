package janggi.domain.moveRules;

import janggi.domain.Direction;
import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Team;
import janggi.domain.board.Board;
import java.util.List;

public class ZolMoveRule implements MoveRule {

    @Override
    public List<Route> findRoutes(Team team) {
        if (team == Team.CHO) {
            Route route1 = new Route(List.of(Direction.NORTH));
            Route route2 = new Route(List.of(Direction.WEST));
            Route route3 = new Route(List.of(Direction.EAST));
            return List.of(route1, route2, route3);
        }
        Route route1 = new Route(List.of(Direction.SOUTH));
        Route route2 = new Route(List.of(Direction.WEST));
        Route route3 = new Route(List.of(Direction.EAST));
        return List.of(route1, route2, route3);
    }

    @Override
    public List<Position> calculateAvailablePositions(Position position, Team team, Board board) {
        return List.of();
    }
}
