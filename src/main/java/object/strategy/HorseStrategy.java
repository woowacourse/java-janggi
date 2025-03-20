package object.strategy;

import java.util.List;
import object.Coordinate;
import object.Route;
import object.piece.Team;

public class HorseStrategy implements MoveStrategy {

    private final List<Route> canMoveDirections = List.of(
            new Route(List.of(new Coordinate(1, 0), new Coordinate(1, -1))),
            new Route(List.of(new Coordinate(1, 0), new Coordinate(1, 1))),
            new Route(List.of(new Coordinate(-1, 0), new Coordinate(-1, -1))),
            new Route(List.of(new Coordinate(-1, 0), new Coordinate(-1, 1))),
            new Route(List.of(new Coordinate(0, 1), new Coordinate(1, 1))),
            new Route(List.of(new Coordinate(0, 1), new Coordinate(-1, 1))),
            new Route(List.of(new Coordinate(0, -1), new Coordinate(1, 1))),
            new Route(List.of(new Coordinate(0, -1), new Coordinate(-1, -1)))
    );

    @Override
    public Route getLegalRoute(Coordinate startCoordinate, Coordinate endCoordinate, Team team) {
        return getLegalRoute(startCoordinate, endCoordinate, canMoveDirections);
    }
}
