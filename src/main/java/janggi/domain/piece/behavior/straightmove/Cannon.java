package janggi.domain.piece.behavior.straightmove;

import janggi.domain.move.Position;
import janggi.domain.move.Vector;
import janggi.domain.piece.BoardPositionInfo;
import janggi.domain.piece.PieceType;
import java.util.Set;

public final class Cannon extends StraightMoveBehavior {

    @Override
    public String toName() {
        return PieceType.CANNON.getName();
    }

    @Override
    public int toScore() {
        return PieceType.CANNON.getScore();
    }

    @Override
    public boolean isCannon() {
        return true;
    }

    @Override
    public void searchAvailableMoves(Set<Position> result, BoardPositionInfo boardPositionInfo, Vector vector) {

        searchAvailableMoves(result, boardPositionInfo, vector, boardPositionInfo.hasPiece());
    }

    public void searchAvailableMoves(Set<Position> result, BoardPositionInfo boardPositionInfo, Vector vector,
                                     boolean hasPassed) {
        Position currentPosition = boardPositionInfo.position();

        if (currentPosition.canNotMove(vector) || boardPositionInfo.isCannon()) {
            return;
        }

        Position nextPosition = currentPosition.moveToNextPosition(vector);
        BoardPositionInfo boardNextPositionInfo = boardPositionInfo.movePosition(nextPosition);
        if (boardNextPositionInfo.isCannon()) {
            return;
        }

        if (hasPassed && boardNextPositionInfo.hasPiece() && boardNextPositionInfo.isNotSameSide()) {
            result.add(nextPosition);
            return;
        }

        if (hasPassed && boardNextPositionInfo.hasPiece()) {
            return;
        }

        if (hasPassed) {
            result.add(nextPosition);
            searchAvailableMoves(result, boardNextPositionInfo, vector, true);
        }

        searchAvailableMoves(result, boardNextPositionInfo, vector, boardNextPositionInfo.hasPiece());
    }
}
