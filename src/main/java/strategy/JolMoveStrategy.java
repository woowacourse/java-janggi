package strategy;

import java.util.List;
import piece.Position;
import piece.Route;

public class JolMoveStrategy implements MoveStrategy {

    private final List<Route> canMoveDirections = List.of(
            new Route(
                    List.of(new Position(0, 1))
            ),
            new Route(
                    List.of(new Position(0, -1))
            ),
            new Route(
                    List.of(new Position(1, 0))
            )
    );

    @Override
    public Route getLegalRoute(Position startPosition, Position endPosition) {
        return getLegalRoute(startPosition, endPosition, canMoveDirections);
    }
}
