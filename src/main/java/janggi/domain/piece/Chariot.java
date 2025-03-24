package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.Vector;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Chariot extends Piece {

    private static final List<Vector> VECTORS = List.of(
            new Vector(1, 0),
            new Vector(0, -1),
            new Vector(0, 1),
            new Vector(-1, 0)
    );

    public Chariot(Side side) {
        super(side);
    }

    @Override
    public Set<Position> generateAvailableMovePositions(Map<Position, Piece> pieces, Position currentPosition) {
        Set<Position> result = new HashSet<>();
        for (Vector vector : VECTORS) {
            currentPosition.calculateNextPosition(vector)
                    .ifPresent(movePosition -> searchAvailableMoves(result, pieces, movePosition, vector));
        }

        return result;
    }

    public void searchAvailableMoves(Set<Position> result, Map<Position, Piece> pieces, Position currentPosition, Vector vector) {
        if (pieces.containsKey(currentPosition)) {
            addPositionIfNotSameSide(result, pieces, currentPosition);
            return;
        }
        result.add(currentPosition);

        if (currentPosition.canNotMove(vector)) {
            return;
        }
        Position nextPosition = currentPosition.moveToNextPosition(vector);

        searchAvailableMoves(result, pieces, nextPosition, vector);
    }

    private void addPositionIfNotSameSide(Set<Position> result, Map<Position, Piece> pieces, Position currentPosition) {
        Piece targetPiece = pieces.get(currentPosition);
        if (targetPiece.isSameSide(side)) {
            return;
        }
        result.add(currentPosition);
    }
}
