package janggi.domain.piece;

import janggi.domain.piece.movement.Movement;

import java.util.List;
import java.util.Map;

public abstract class PathMovingPiece extends Piece {

    protected PathMovingPiece(String name, Position position, Team team) {
        super(name, position, team);
    }

    @Override
    public void validatePositionToMove(Map<Position, Piece> pieces, Position positionToMove) {
        List<Movement> movements = findMovements(positionToMove);
        Position currentPosition = getPosition();
        for (Movement movement : movements) {
            currentPosition = currentPosition.plus(movement.getX(), movement.getY());
            if (!checkPieceCondition(pieces.get(currentPosition), currentPosition)) {
                throw new IllegalArgumentException("불가능한 이동입니다");
            }
        }
        if (!currentPosition.equals(positionToMove)) {
            throw new IllegalArgumentException("불가능한 이동입니다");
        }
    }

    protected abstract boolean checkPieceCondition(Piece checkingPiece, Position checkingPosition);

    protected abstract List<Movement> findMovements(Position positionToMove);
}
