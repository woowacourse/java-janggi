package strategy;

import java.util.List;
import piece.Position;
import piece.Route;
import piece.Team;

public class MaMoveStrategy implements MoveStrategy {

    private final List<Route> canMoveDirections = List.of(
            new Route(List.of(new Position(1, 0), new Position(1, -1))),
            new Route(List.of(new Position(1, 0), new Position(1, 1))),
            new Route(List.of(new Position(-1, 0), new Position(-1, -1))),
            new Route(List.of(new Position(-1, 0), new Position(-1, 1))),
            new Route(List.of(new Position(0, 1), new Position(1, 1))),
            new Route(List.of(new Position(0, 1), new Position(-1, 1))),
            new Route(List.of(new Position(0, -1), new Position(1, 1))),
            new Route(List.of(new Position(0, -1), new Position(-1, -1)))
    );

    @Override
    public Route getLegalRoute(Position startPosition, Position endPosition, Team team) {
        return getLegalRoute(startPosition, endPosition, canMoveDirections);
    }
}
