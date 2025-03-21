package janggi.domain.piece.behavior;

import janggi.domain.Board;
import janggi.domain.move.Position;
import janggi.domain.Side;
import janggi.domain.move.Vector;
import janggi.domain.piece.PieceBehavior;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cannon implements PieceBehavior {

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
                            searchAvailableMoves(result, board, movePosition, vector, side,
                                    board.hasPiece(movePosition)));
        }

        return result;
    }

    @Override
    public String toName() {
        return "포";
    }

    public void searchAvailableMoves(Set<Position> result, Board board, Position currentPosition, Vector vector,
                                     Side side,
                                     boolean hasPassed) {
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
