package janggi.domain.piece.behavior.straightmove;

import janggi.domain.Board;
import janggi.domain.Side;
import janggi.domain.move.Position;
import janggi.domain.move.Vector;
import janggi.domain.piece.PieceBehavior;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class StraightMoveBehavior implements PieceBehavior {

    public final Set<Position> generateAvailableMovePositions(Board board, Side side, Position position) {
        Set<Position> result = new HashSet<>();
        for (Vector vector : getVectors()) {
            position.calculateNextPosition(vector)
                    .ifPresent(movePosition ->
                            searchAvailableMoves(result, board, movePosition, vector, side));
        }

        return result;
    }

    protected abstract List<Vector> getVectors();

    protected abstract void searchAvailableMoves(Set<Position> result, Board board, Position currentPosition,
                                                 Vector vector,
                                                 Side side);
}
