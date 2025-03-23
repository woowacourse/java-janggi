package move;

import exception.InvalidMovePosition;
import java.util.ArrayList;
import java.util.List;
import piece.Piece;
import piece.Pieces;
import piece.Position;
import piece.Route;
import piece.Team;

public interface MoveBehavior {

    @FunctionalInterface
    interface MoveBehaviorThrowingPredicate {
        boolean test();
    }

    default void throwInvalidMoveBehaviorByCondition(MoveBehaviorThrowingPredicate throwCondition) {
        if (throwCondition.test()) {
            throw new InvalidMovePosition();
        }
    }

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
        throw new InvalidMovePosition();
    }


    default Position move(Position destination, Pieces onRoutePieces, Team moveTeam) {
        for (Piece piece : onRoutePieces.getPieces()) {
            throwInvalidMoveBehaviorByCondition(() -> !piece.isSamePosition(destination));
            throwInvalidMoveBehaviorByCondition(() -> piece.isSameTeam(moveTeam));
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
