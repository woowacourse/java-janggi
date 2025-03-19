package strategy;

import java.util.List;
import piece.Piece;
import piece.Pieces;
import piece.Position;
import piece.Route;
import piece.Team;

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
    private static final String INVALID_MOVE_LOCATION = "이동불가능한 위치입니다.";

    @Override
    public Route getLegalRoute(Position startPosition, Position endPosition) {
        return getLegalRoute(startPosition, endPosition, canMoveDirections);
    }

    @Override
    public Position move(Pieces onRoutePieces, Position destination, Team moveTeam) {
        for (Piece piece : onRoutePieces.getPieces()) {
            if (piece.isSamePosition(destination) && piece.team() == moveTeam) {
                throw new IllegalArgumentException(INVALID_MOVE_LOCATION);
            }
        }
        return destination;
    }
}
