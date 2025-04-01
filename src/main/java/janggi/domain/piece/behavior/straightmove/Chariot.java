package janggi.domain.piece.behavior.straightmove;

import janggi.domain.move.Position;
import janggi.domain.move.Vector;
import janggi.domain.piece.BoardPositionInfo;
import janggi.domain.piece.PieceType;
import java.util.Set;

public final class Chariot extends StraightMoveBehavior {

    @Override
    protected void searchAvailableMoves(Set<Position> result, BoardPositionInfo boardPositionInfo, Vector vector) {
        Position currentPosition = boardPositionInfo.position();

        if (boardPositionInfo.hasPiece()) {
            addPositionIfNotSameSide(result, boardPositionInfo);
            return;
        }
        result.add(currentPosition);

        if (boardPositionInfo.canNotMove(vector)) {
            return;
        }
        Position nextPosition = currentPosition.moveToNextPosition(vector);

        searchAvailableMoves(result, boardPositionInfo.movePosition(nextPosition), vector);
    }

    @Override
    public String toName() {
        return PieceType.CHARIOT.getName();
    }

    @Override
    public int toScore() {
        return PieceType.CHARIOT.getScore();
    }

    private void addPositionIfNotSameSide(Set<Position> result, BoardPositionInfo boardPositionInfo) {
        if (boardPositionInfo.isNotSameSide()) {
            result.add(boardPositionInfo.position());
        }
    }
}
