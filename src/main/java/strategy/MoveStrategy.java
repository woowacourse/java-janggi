package strategy;

import java.util.ArrayList;
import java.util.List;
import piece.Piece;
import piece.Pieces;
import piece.Position;
import piece.Route;
import piece.Team;

public interface MoveStrategy {

    String INVALID_POSITION = "도달할 수 없는 위치입니다.";

    Route getLegalRoute(Position startPosition, Position endPosition, Team team);

    default Route getLegalRoute(Position startPosition, Position endPosition, List<Route> canMoveDirections) {
        for (Route canMoveDirection : canMoveDirections) {
            List<Position> moveRoute = new ArrayList<>();
            Position currentPosition = startPosition;
            currentPosition = movePosition(canMoveDirection, currentPosition, moveRoute);
            if (currentPosition.equals(endPosition)) {
                return new Route(moveRoute);
            }
        }
        throw new IllegalArgumentException(INVALID_POSITION);
    }

    default Position move(Position destination, Pieces onRoutePieces, Team moveTeam) {
        for (Piece piece : onRoutePieces.getPieces()) {
            if (!piece.isSamePosition(destination)) {
                throw new IllegalArgumentException(INVALID_POSITION);
            }
            if (piece.isSameTeam(moveTeam)) {
                throw new IllegalArgumentException(INVALID_POSITION);
            }
        }

        return destination;
    }

    private Position movePosition(Route canMoveDirection, Position currentPosition, List<Position> moveRoute) {
        for (Position position : canMoveDirection.positions()) {
            currentPosition = currentPosition.add(position);
            moveRoute.add(currentPosition);
        }
        return currentPosition;
    }
}
