package janggi.domain.piece.behavior.straightmove;

import janggi.domain.Board;
import janggi.domain.Side;
import janggi.domain.move.Position;
import janggi.domain.move.Vector;
import java.util.Set;

public final class Cannon extends StraightMoveBehavior {

    @Override
    public String toName() {
        return "포";
    }

    @Override
    public boolean isCannon() {
        return true;
    }

    @Override
    public void searchAvailableMoves(Set<Position> result, Board board, Position currentPosition, Vector vector,
                                     Side side) {
        searchAvailableMoves(result, board, currentPosition, vector, side, board.hasPiece(currentPosition));
    }

    public void searchAvailableMoves(Set<Position> result, Board board, Position currentPosition, Vector vector,
                                     Side side, boolean hasPassed) {
        if (currentPosition.canNotMove(vector) || board.isCannon(currentPosition)) {
            return;
        }

        Position nextPosition = currentPosition.moveToNextPosition(vector);
        if (board.isCannon(nextPosition)) {
            return;
        }

        if (hasPassed && board.hasPiece(nextPosition) && !board.isSameSide(side, nextPosition)) {
            result.add(nextPosition);
            return;
        }

        if (hasPassed && board.hasPiece(nextPosition)) {
            return;
        }

        if (hasPassed) {
            result.add(nextPosition);
            searchAvailableMoves(result, board, nextPosition, vector, side, true);
        }

        searchAvailableMoves(result, board, nextPosition, vector, side, board.hasPiece(nextPosition));
    }
}
