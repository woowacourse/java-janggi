package strategy;

import java.util.ArrayList;
import java.util.List;
import piece.Position;
import piece.Route;

public class JolMoveStrategy implements MoveStrategy{
    List<Route> canMoveRoutes = List.of(
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
        for (Route canMoveRoute : canMoveRoutes) {
            List<Position> moveRoute = new ArrayList<>();
            Position currentPosition = startPosition;
            for (Position position : canMoveRoute.getPositions()) {
                currentPosition = currentPosition.add(position);
                moveRoute.add(currentPosition);
            }
            if (currentPosition.equals(endPosition)) {
                return new Route(moveRoute);
            }
        }
        throw new IllegalArgumentException("도달할 수 없는 위치입니다.");
    }

    @Override
    public boolean isAbleToMove() {
        return false;
    }

}
