package janggi.domain.moveRules;

import janggi.domain.Direction;
import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Team;
import janggi.domain.board.Board;
import java.util.List;

public class KingMoveRule implements MoveRule {

    @Override
    public List<Route> findRoutes(Team team) {
        Route route1 = new Route(List.of(Direction.NORTH));
        Route route2 = new Route(List.of(Direction.EAST));
        Route route3 = new Route(List.of(Direction.SOUTH));
        Route route4 = new Route(List.of(Direction.WEST));
        Route route5 = new Route(List.of(Direction.NORTH_WEST));
        Route route6 = new Route(List.of(Direction.NORTH_EAST));
        Route route7 = new Route(List.of(Direction.SOUTH_WEST));
        Route route8 = new Route(List.of(Direction.SOUTH_EAST));
        return List.of(route1, route2, route3, route4, route5, route6, route7, route8);
    }

    @Override
    public List<Position> calculateAvailablePositions(Position position, Team team, Board board) {
        return List.of();
    }
}
