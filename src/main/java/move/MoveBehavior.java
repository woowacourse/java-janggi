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
import piece.position.JanggiPosition;

public abstract class MoveBehavior {

    void throwInvalidMoveBehaviorByCondition(MoveBehaviorThrowingPredicate throwCondition) {
        if (throwCondition.test()) {
            throw new InvalidMovePosition();
        }
    }

    List<JanggiPosition> calculateLegalRoute(JanggiPosition startPosition, JanggiPosition endPosition,
                                             List<Directions> canMoveDirections) {
        for (Directions canMoveDirection : canMoveDirections) {
            JanggiPosition currentPosition = startPosition;
            List<JanggiPosition> movePositions = new ArrayList<>();
            currentPosition = movePosition(canMoveDirection, currentPosition, movePositions);
            if (currentPosition.equals(endPosition)) {
                return Collections.unmodifiableList(movePositions);
            }
        }
        throw new InvalidMovePosition();
    }


    public JanggiPosition move(JanggiPosition destination, Pieces onRoutePieces, Team moveTeam) {
        for (Piece piece : onRoutePieces.getPieces()) {
            throwInvalidMoveBehaviorByCondition(() -> !piece.isSamePosition(destination));
            throwInvalidMoveBehaviorByCondition(() -> piece.isSameTeam(moveTeam));
        }

        return destination;
    }

    private JanggiPosition movePosition(Directions directions, JanggiPosition currentPosition,
                                        List<JanggiPosition> moveRoute) {
        for (Direction direction : directions.getDirections()) {
            currentPosition = currentPosition.add(direction);
            moveRoute.add(currentPosition);
        }
        return currentPosition;
    }

    abstract public PieceType getPieceType();

    abstract public List<JanggiPosition> calculateLegalRoute(JanggiPosition startPosition, JanggiPosition endPosition,
                                                             Team team);

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
