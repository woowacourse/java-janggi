package move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import move.direction.Direction;
import move.direction.Directions;
import piece.Piece;
import piece.PieceType;
import piece.Pieces;
import piece.Team;
import piece.position.Position;

public abstract class MoveBehavior {

    void throwInvalidMoveBehaviorByCondition(MoveBehaviorThrowingPredicate throwCondition) {
        if (throwCondition.test()) {
            throw new InvalidMovePosition();
        }
    }

    List<Position> calculateLegalRoute(Position startPosition, Position endPosition,
                                       List<Directions> canMoveDirections) {
        for (Directions canMoveDirection : canMoveDirections) {
            Position currentPosition = startPosition;
            List<Position> movePositions = new ArrayList<>();
            currentPosition = movePosition(canMoveDirection, currentPosition, movePositions);
            if (currentPosition.equals(endPosition)) {
                return Collections.unmodifiableList(movePositions);
            }
        }
        throw new InvalidMovePosition();
    }


    public Position move(Position destination, Pieces onRoutePieces, Team moveTeam) {
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

    abstract public PieceType getPieceType();

    abstract public List<Position> calculateLegalRoute(Position startPosition, Position endPosition, Team team);

    public boolean isSameType(PieceType pieceType) {
        return getPieceType().isSameType(pieceType);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof MoveBehavior) {
            return isSameType(((MoveBehavior) object).getPieceType());
        }
        return false;
    }
}
