package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.piece.movement.Movement;
import java.util.List;
import java.util.Map;

public abstract class PathMovingPiece extends Piece {

    protected PathMovingPiece(PieceType pieceType, Position position, Team team) {
        super(pieceType, position, team);
    }

    @Override
    public void validatePositionToMove(Map<Position, Piece> pieces, Position positionToMove) {
        List<Movement> movements = findMovements(positionToMove);
        Position currentPosition = getPosition();
        for (Movement movement : movements) {
            if (hasPieceOnPath(pieces.get(currentPosition))) {
                throw new IllegalArgumentException("불가능한 이동입니다");
            }
            currentPosition = currentPosition.plus(movement.getX(), movement.getY());
        }
        if (!currentPosition.equals(positionToMove)) {
            throw new IllegalArgumentException("불가능한 이동입니다");
        }
    }

    private boolean hasPieceOnPath(Piece pieceInPositionToMove) {
        return !pieceInPositionToMove.isNone();
    }

    protected abstract List<Movement> findMovements(Position positionToMove);
}
