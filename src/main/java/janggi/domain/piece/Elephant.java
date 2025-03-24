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

public class Elephant extends Piece {

    private static final List<Vectors> VECTORS_LIST = List.of(
            Vectors.of(new Vector(1, 0), new Vector(2, -1), new Vector(3, -2)),
            Vectors.of(new Vector(1, 0), new Vector(2, 1), new Vector(3, 2))
    );

    public Elephant(Side side) {
        super(side);
    }

    @Override
    public Set<Position> generateAvailableMovePositions(Map<Position, Piece> pieces, Position currentPosition) {
        Set<Position> result = new HashSet<>();
        List<Vectors> rotatedVectors = new ArrayList<>(VECTORS_LIST);
        for (int i = 0; i < 4; i++) {
            searchAvailableMoves(result, pieces, currentPosition, rotatedVectors);
            rotatedVectors = Vectors.rotate(rotatedVectors);
        }

        return result;
    }

    private void searchAvailableMoves(Set<Position> result, Map<Position, Piece> pieces, Position position,
                                      List<Vectors> vectorsList) {
        for (Vectors vectors : vectorsList) {
            List<Vector> vectorRoute = vectors.vectors();
            searchAvailableMove(result, pieces, position, vectorRoute);
        }
    }

    private void searchAvailableMove(Set<Position> result, Map<Position, Piece> pieces, Position position,
                                     List<Vector> vectors) {
        if (canNotMove(vectors, position) || hasNoAvailableMiddleMove(vectors, position, pieces)) {
            return;
        }

        Position finalPosition = position.moveToNextPosition(vectors.get(2));

        if (canMoveToPosition(pieces, finalPosition)) {
            result.add(finalPosition);
        }
    }

    private boolean canNotMove(List<Vector> vectors, Position currentPosition) {
        return vectors.stream()
                .anyMatch(currentPosition::canNotMove);
    }

    private boolean hasNoAvailableMiddleMove(List<Vector> vectors, Position currentPosition, Map<Position, Piece> pieces) {
        Position midPosition1 = currentPosition.moveToNextPosition(vectors.get(0));
        Position midPosition2 = currentPosition.moveToNextPosition(vectors.get(1));

        return !(checkAvailableMiddleMove(midPosition1, pieces) && checkAvailableMiddleMove(midPosition2, pieces));
    }

    private boolean checkAvailableMiddleMove(Position midPosition, Map<Position, Piece> pieces) {
        return midPosition != null && !pieces.containsKey(midPosition);
    }

    private boolean canMoveToPosition(Map<Position, Piece> pieces, Position position) {
        if (!pieces.containsKey(position)) {
            return true;
        }
        Piece nextPiece = pieces.get(position);
        return !nextPiece.isSameSide(side);
    }
}
