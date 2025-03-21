package janggi.domain.piece.behavior.straightmove;

import janggi.domain.Board;
import janggi.domain.Side;
import janggi.domain.move.Position;
import janggi.domain.move.Vector;
import java.util.List;
import java.util.Set;

public class Chariot extends StraightMoveBehavior {

    @Override
    protected List<Vector> getVectors() {
        return List.of(
                new Vector(1, 0),
                new Vector(0, -1),
                new Vector(0, 1),
                new Vector(-1, 0));
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
