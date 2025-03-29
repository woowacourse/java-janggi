package janggi.piece;

import janggi.direction.Movements;
import janggi.direction.PieceMovement;
import janggi.position.Position;
import janggi.direction.PieceMoveRule;
import java.util.Optional;

public class Piece {

    private final PieceMoveRule pieceMoveRule;
    private Position position;

    public Piece(final PieceMoveRule pieceMoveRule, final Position position) {
        this.pieceMoveRule = pieceMoveRule;
        this.position = position;
    }

    public boolean isSamePosition(final Position givenPosition) {
        return position.equals(givenPosition);
    }

    public void updatePosition(final Position arrivalPosition) {
        position = arrivalPosition;
    }

    public void validateMovement(final Position currentPosition, final Position arrivalPosition,
                                 final Board board) {
        if (getPieceMovement().canNotMoveDiagonal()) {
            final Optional<Movements> optionalMovements = PalaceMovement.getMovements(currentPosition);
            optionalMovements.ifPresent(pieceMoveRule::addMovement);
            pieceMoveRule.validatePath(currentPosition, arrivalPosition, board);
            optionalMovements.ifPresent(pieceMoveRule::deleteMovement);
            return;
        }
        pieceMoveRule.validatePath(currentPosition, arrivalPosition, board);
    }

    public boolean isObstacleJumping() {
        return getPieceMovement() == PieceMovement.CANNON;
    }

    public boolean matchPieceMovement(final PieceMovement givenPieceMovement) {
        return getPieceMovement() == givenPieceMovement;
    }


    public Position getPosition() {
        return position;
    }

    public PieceMovement getPieceMovement() {
        return pieceMoveRule.getPieceMovement();
    }
}
