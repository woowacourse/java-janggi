package janggi.domain.piece.behavior.straightmove;

import janggi.domain.Board;
import janggi.domain.Side;
import janggi.domain.move.Movement;
import janggi.domain.move.Position;
import janggi.domain.move.Vector;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public final class Cannon extends StraightMoveBehavior {

    @Override
    public String toName() {
        return "포";
    }

    @Override
    protected List<Vector> getVectors() {
        return Stream.of(
                        Movement.DOWN, Movement.LEFT, Movement.RIGHT, Movement.UP
                )
                .map(Movement::getVector)
                .toList();
    }

    @Override
    public void searchAvailableMoves(Set<Position> result, Board board, Position currentPosition, Vector vector,
                                     Side side) {
        searchAvailableMoves(result, board, currentPosition, vector, side, board.hasPiece(currentPosition));
    }

    public void searchAvailableMoves(Set<Position> result, Board board, Position currentPosition, Vector vector,
                                     Side side,
                                     boolean hasPassed) {
        if (currentPosition.canNotMove(vector) || isCannon(board, currentPosition)) {
            return;
        }

        Position nextPosition = currentPosition.moveToNextPosition(vector);
        if (isCannon(board, nextPosition)) {
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

    private boolean isCannon(Board board, Position position) {
        return board.hasPiece(position) && board.getPiece(position).getPieceBehavior() instanceof Cannon;
    }
}
