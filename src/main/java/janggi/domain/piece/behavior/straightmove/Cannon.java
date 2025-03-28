package janggi.domain.piece.behavior.straightmove;

import janggi.domain.Board;
import janggi.domain.Team;
import janggi.domain.move.Position;
import janggi.domain.move.Vector;
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
    public void searchAvailableMoves(Set<Position> result, Board board, Position currentPosition, Vector vector,
                                     Team team) {
        searchAvailableMoves(result, board, currentPosition, vector, team, board.hasPiece(currentPosition));
    }

    public void searchAvailableMoves(Set<Position> result, Board board, Position currentPosition, Vector vector,
                                     Team team, boolean hasPassed) {
        if (currentPosition.canNotMove(vector) || board.isCannon(currentPosition)) {
            return;
        }

        Position nextPosition = currentPosition.moveToNextPosition(vector);
        if (board.isCannon(nextPosition)) {
            return;
        }

        if (hasPassed && board.hasPiece(nextPosition) && !board.isSameSide(team, nextPosition)) {
            result.add(nextPosition);
            return;
        }

        if (hasPassed && board.hasPiece(nextPosition)) {
            return;
        }

        if (hasPassed) {
            result.add(nextPosition);
            searchAvailableMoves(result, board, nextPosition, vector, team, true);
        }

        searchAvailableMoves(result, board, nextPosition, vector, team, board.hasPiece(nextPosition));
    }
}
