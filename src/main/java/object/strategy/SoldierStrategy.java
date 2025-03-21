package object.strategy;

import java.util.List;
import java.util.Map;
import object.Coordinate;
import object.Route;
import object.piece.PieceType;
import object.piece.Team;

public class SoldierStrategy implements MoveStrategy {

    private static final Map<Team, List<Route>> teamCanMoveDirection;

    static {
        final List<Route> blueCanMoveDirections = List.of(
                new Route(List.of(new Coordinate(0, 1))),
                new Route(List.of(new Coordinate(0, -1))),
                new Route(List.of(new Coordinate(1, 0)))
        );
        final List<Route> redCanMoveDirections = List.of(
                new Route(List.of(new Coordinate(0, 1))),
                new Route(List.of(new Coordinate(0, -1))),
                new Route(List.of(new Coordinate(-1, 0)))
        );

        teamCanMoveDirection = Map.of(Team.BLUE, blueCanMoveDirections, Team.RED, redCanMoveDirections);
    }

    @Override
    public Route getLegalRoute(Coordinate startCoordinate, Coordinate endCoordinate, Team team) {
        return getLegalRoute(startCoordinate, endCoordinate, teamCanMoveDirection.get(team));
    }

    public PieceType getPieceType() {
        return PieceType.SOLIDER;
    }
}
