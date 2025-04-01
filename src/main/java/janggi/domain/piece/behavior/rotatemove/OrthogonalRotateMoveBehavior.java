package janggi.domain.piece.behavior.rotatemove;

import janggi.domain.move.Position;
import janggi.domain.move.Vector;
import janggi.domain.move.Vectors;
import janggi.domain.piece.BoardPositionInfo;
import janggi.domain.piece.PieceBehavior;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public abstract class OrthogonalRotateMoveBehavior implements PieceBehavior {

    private static final int TOTAL_ROTATIONS = 4;

    @Override
    public final Set<Position> generateAvailableMovePositions(BoardPositionInfo boardPositionInfo) {
        Set<Position> result = new HashSet<>();
        List<Vectors> rotatedVectors = new ArrayList<>(getVectorsList());

        for (int i = 0; i < TOTAL_ROTATIONS; i++) {
            rotatedVectors = Vectors.rotate(rotatedVectors);
            searchAvailableMoves(result, boardPositionInfo, rotatedVectors);
        }

        return result;
    }

    protected abstract List<Vectors> getVectorsList();

    protected abstract void searchAvailableMoves(Set<Position> result, BoardPositionInfo boardPositionInfo,
                                                 List<Vectors> vectorsList);

    protected abstract void searchAvailableMove(Set<Position> result, BoardPositionInfo boardPositionInfo,
                                                Vectors vectors);

    protected boolean canNotMove(Vectors vectors, Position currentPosition) {
        int size = vectors.size();

        List<Vector> accumulateVectors = IntStream.range(0, size)
                .mapToObj(vectors::accumulate)
                .toList();

        return accumulateVectors
                .stream()
                .allMatch(currentPosition::canNotMove);
    }
}
