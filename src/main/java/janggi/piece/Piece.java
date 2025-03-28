package janggi.piece;

import janggi.direction.PieceMovement;
import janggi.position.Position;
import janggi.strategy.MoveStrategy;
import java.util.Set;

public class Piece {

    private final MoveStrategy moveStrategy;
    private Position position;

    public Piece(final MoveStrategy moveStrategy, final Position position) {
        this.moveStrategy = moveStrategy;
        this.position = position;
    }

    public boolean isSamePosition(final Position givenPosition) {
        return position.equals(givenPosition);
    }

    public void updatePosition(final Position arrivalPosition) {
        position = arrivalPosition;
    }

    public void validateMovement(final Position currentPosition, final Position arrivalPosition,
                                 final Pieces pieces) {
        moveStrategy.validatePath(currentPosition, arrivalPosition, pieces);
    }

    public boolean matchPieceMovement(final PieceMovement givenPieceMovement) {
        return getPieceMovement() == givenPieceMovement;
    }

    public Position getPosition() {
        return position;
    }

    public PieceMovement getPieceMovement() {
        return moveStrategy.getPieceMovement();
    }
}
