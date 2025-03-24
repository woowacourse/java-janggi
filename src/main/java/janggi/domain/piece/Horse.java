package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.Vector;
import janggi.domain.Vectors;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Horse extends Piece {

    private static final List<Vectors> VECTOR_ROUTE_LIST = List.of(
            Vectors.of(new Vector(1, 0), new Vector(2, -1)),
            Vectors.of(new Vector(1, 0), new Vector(2, 1))
    );

    public Horse(Side side) {
        super(side);
    }

    @Override
    public Set<Position> generateAvailableMovePositions(Map<Position, Piece> pieces, Position currentPosition) {
        Set<Position> result = new HashSet<>();
        List<Vectors> rotatedVectors = new ArrayList<>(VECTOR_ROUTE_LIST);
        for (int i = 0; i < 4; i++) {
            searchAvailableMoves(result, pieces, currentPosition, rotatedVectors);
            rotatedVectors = Vectors.rotate(rotatedVectors);
        }

        return result;
    }

    private void searchAvailableMoves(Set<Position> result, Map<Position, Piece> pieces, Position position,
                                      List<Vectors> vectorsList) {
        for (Vectors vectors : vectorsList) {
            searchAvailableMove(result, pieces, position, vectors.vectors());
        }
    }

    private void searchAvailableMove(Set<Position> result, Map<Position, Piece> pieces, Position position,
                                     List<Vector> vectors) {
        if (canNotMove(vectors, position)) {
            return;
        }

        Position midPosition = position.moveToNextPosition(vectors.get(0));
        Position finalPosition = position.moveToNextPosition(vectors.get(1));

        if (!pieces.containsKey(midPosition) && canMoveToPosition(pieces, finalPosition)) {
            result.add(finalPosition);
        }
    }

    private boolean canNotMove(List<Vector> vectors, Position currentPosition) {
        return vectors.stream().anyMatch(currentPosition::canNotMove);
    }

    private boolean canMoveToPosition(Map<Position, Piece> pieces, Position position) {
        if (!pieces.containsKey(position)) {
            return true;
        }
        Piece nextPiece = pieces.get(position);
        return !nextPiece.isSameSide(side);
    }
}
