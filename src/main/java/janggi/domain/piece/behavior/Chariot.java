package janggi.domain.piece.behavior;

import janggi.domain.Board;
import janggi.domain.move.Position;
import janggi.domain.Side;
import janggi.domain.move.Vector;
import janggi.domain.piece.PieceBehavior;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Chariot implements PieceBehavior {

    private static final List<Vector> VECTORS = List.of(
            new Vector(1, 0),
            new Vector(0, -1),
            new Vector(0, 1),
            new Vector(-1, 0));

    @Override
    public Set<Position> generateAvailableMovePositions(Board board, Side side, Position position) {
        Set<Position> result = new HashSet<>();
        for (Vector vector : VECTORS) {
            position.calculateNextPosition(vector)
                    .ifPresent(movePosition ->
                            searchAvailableMoves(result, board, movePosition, vector, side));
        }

        return result;
    }

    @Override
    public String toName() {
        return "차";
    }

    public void searchAvailableMoves(Set<Position> result, Board board, Position currentPosition, Vector vector,
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

    private void addPositionIfNotSameSide(Set<Position> result, Board board, Position currentPosition, Side side) {
        if (board.isSameSide(side, currentPosition)) {
            return;
        }
        result.add(currentPosition);
    }
}
