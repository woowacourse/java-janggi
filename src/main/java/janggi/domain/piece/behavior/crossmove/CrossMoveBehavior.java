package janggi.domain.piece.behavior.crossmove;

import janggi.domain.Board;
import janggi.domain.Side;
import janggi.domain.move.Position;
import janggi.domain.move.Vector;
import janggi.domain.move.Vectors;
import janggi.domain.piece.PieceBehavior;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class CrossMoveBehavior implements PieceBehavior {

    @Override
    public final Set<Position> generateAvailableMovePositions(Board board, Side side, Position position) {
        Set<Position> result = new HashSet<>();
        List<Vectors> rotatedVectors = new ArrayList<>(getVectorsList());

        for (int i = 0; i < 4; i++) {
            rotatedVectors = Vectors.rotate(rotatedVectors);
            searchAvailableMoves(result, board, position, rotatedVectors, side);
        }

        return result;
    }

    protected abstract List<Vectors> getVectorsList();

    protected abstract void searchAvailableMoves(Set<Position> result, Board board, Position position,
                                                 List<Vectors> vectorsList,
                                                 Side side);

    protected abstract void searchAvailableMove(Set<Position> result, Board board, Position position, Side side,
                                                List<Vector> vectors);

    protected boolean canNotMove(List<Vector> vectors, Position currentPosition) {
        return vectors.stream()
                .allMatch(currentPosition::canNotMove);
    }
}
