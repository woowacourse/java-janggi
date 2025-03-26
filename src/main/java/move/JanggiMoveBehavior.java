package move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import move.direction.Direction;
import move.direction.Directions;
import piece.Piece;
import piece.PieceScore;
import piece.PieceType;
import piece.Pieces;
import piece.player.Team;
import piece.position.JanggiPosition;

public abstract class JanggiMoveBehavior {

    void throwInvalidMoveBehaviorByCondition(MoveBehaviorThrowingPredicate throwCondition) {
        if (throwCondition.test()) {
            throw new InvalidMovePosition();
        }
    }

    private JanggiPosition movePosition(Directions directions, JanggiPosition currentPosition,
                                        List<JanggiPosition> moveRoute) {
        for (Direction direction : directions.getDirections()) {
            currentPosition = currentPosition.add(direction);
            moveRoute.add(currentPosition);
        }
        return currentPosition;
    }

    protected List<JanggiPosition> calculateLegalRoute(JanggiPosition startPosition, JanggiPosition endPosition,
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

    protected boolean isDiagonalGungsungCase(JanggiPosition startPosition, JanggiPosition endPosition) {
        if (!startPosition.isPositionDiagonalGungPosition() || !endPosition.isPositionDiagonalGungPosition()) {
            return false;
        }
        return startPosition.isSameDiagonal(endPosition);
    }

    protected boolean isInsideGungsungCase(JanggiPosition startPosition, JanggiPosition endPosition) {
        return startPosition.isInsideGungsung(startPosition) && endPosition.isInsideGungsung(endPosition);
    }

    public boolean isSameType(PieceType pieceType) {
        return getPieceType().isSameType(pieceType);
    }

    public JanggiPosition moveOnRoute(JanggiPosition destination, Pieces onRoutePieces, Team moveTeam) {
        for (Piece piece : onRoutePieces.getPieces()) {
            throwInvalidMoveBehaviorByCondition(() -> !piece.isSamePosition(destination));
            throwInvalidMoveBehaviorByCondition(() -> piece.isSameTeam(moveTeam));
        }

        return destination;
    }

    public PieceScore getPieceScore() {
        return PieceScore.from(getPieceType());
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof JanggiMoveBehavior) {
            return isSameType(((JanggiMoveBehavior) object).getPieceType());
        }
        return false;
    }

    abstract public PieceType getPieceType();

    abstract public List<JanggiPosition> calculateLegalRoute(JanggiPosition startPosition, JanggiPosition endPosition,
                                                             Team team);
}
