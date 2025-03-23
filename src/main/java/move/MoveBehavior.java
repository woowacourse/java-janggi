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

    default Route getLegalRoute(Position startPosition, Position endPosition, List<Directions> canMoveDirections) {
        for (Directions canMoveDirection : canMoveDirections) {
            Position currentPosition = startPosition;
            List<Position> movePositions = new ArrayList<>();
            currentPosition = movePosition(canMoveDirection, currentPosition, movePositions);
            if (currentPosition.equals(endPosition)) {
                return new Route(movePositions);
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

    private Position movePosition(Directions directions, Position currentPosition, List<Position> moveRoute) {
        for (Direction direction : directions.getDirections()) {
            currentPosition = currentPosition.add(direction);
            moveRoute.add(currentPosition);
        }
        return currentPosition;
    }
}
