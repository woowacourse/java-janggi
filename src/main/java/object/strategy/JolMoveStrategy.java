package object.strategy;

import java.util.List;
import object.Coordinate;
import object.Route;

public class JolMoveStrategy implements MoveStrategy {

    private final List<Route> canMoveDirections = List.of(
            new Route(
                    List.of(new Coordinate(0, 1))
            ),
            new Route(
                    List.of(new Coordinate(0, -1))
            ),
            new Route(
                    List.of(new Coordinate(1, 0))
            )
    );

    @Override
    public Route getLegalRoute(Coordinate startCoordinate, Coordinate endCoordinate) {
        return getLegalRoute(startCoordinate, endCoordinate, canMoveDirections);
    }
}
