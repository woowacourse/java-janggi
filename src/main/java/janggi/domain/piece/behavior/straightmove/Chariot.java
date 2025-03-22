package janggi.domain.piece.behavior.straightmove;

import janggi.domain.Board;
import janggi.domain.Side;
import janggi.domain.move.Movement;
import janggi.domain.move.Position;
import janggi.domain.move.Vector;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public final class Chariot extends StraightMoveBehavior {

    @Override
    protected List<Vector> getVectors() {
        return Stream.of(
                        Movement.DOWN, Movement.LEFT, Movement.RIGHT, Movement.UP
                )
                .map(Movement::getVector)
                .toList();
    }

    @Override
    protected void searchAvailableMoves(Set<Position> result, Board board, Position currentPosition, Vector vector,
                                        Side side) {
        if (board.hasPiece(currentPosition)) {
            addPositionIfNotSameSide(result, board, currentPosition, side);
            return;
        }
        result.add(currentPosition);

        if (currentPosition.canNotMove(vector)) {
            return;
        }
        Position nextPosition = currentPosition.moveToNextPosition(vector);

        searchAvailableMoves(result, board, nextPosition, vector, side);
    }

    @Override
    public String toName() {
        return "차";
    }

    private void addPositionIfNotSameSide(Set<Position> result, Board board, Position currentPosition, Side side) {
        if (board.isSameSide(side, currentPosition)) {
            return;
        }
        result.add(currentPosition);
    }
}
