package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.Vector;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Cannon extends Piece {

    private static final List<Vector> VECTORS = List.of(
            new Vector(1, 0),
            new Vector(0, -1),
            new Vector(0, 1),
            new Vector(-1, 0)
    );

    public Cannon(Side side) {
        super(side);
    }

    @Override
    public Set<Position> generateAvailableMovePositions(Map<Position, Piece> pieces, Position currentPosition) {
        Set<Position> result = new HashSet<>();
        for (Vector vector : VECTORS) {
            currentPosition.calculateNextPosition(vector)
                    .ifPresent(nextPosition -> searchAvailableMoves(result, pieces, nextPosition, vector, pieces.containsKey(nextPosition)));
        }

        return result;
    }

    public void searchAvailableMoves(Set<Position> result, Map<Position, Piece> pieces, Position currentPosition,
                                     Vector vector, boolean hasPassed) {
        if (!canContinueFromPosition(pieces, currentPosition, vector)) {
            return;
        }

        Position nextPosition = currentPosition.moveToNextPosition(vector);

        if (pieces.containsKey(nextPosition) && canCatchPiece(pieces, nextPosition, hasPassed)) {
            result.add(nextPosition);
            return;
        }

        if (hasPassed && !pieces.containsKey(nextPosition)) {
            result.add(nextPosition);
            searchAvailableMoves(result, pieces, nextPosition, vector, true);
        }

        searchAvailableMoves(result, pieces, nextPosition, vector, pieces.containsKey(nextPosition));
    }

    private boolean canContinueFromPosition(Map<Position, Piece> pieces, Position currentPosition, Vector vector) {
        if (currentPosition.canNotMove(vector)) {
            return false;
        }
        if (!pieces.containsKey(currentPosition)) {
            return true;
        }
        Piece currentPiece = pieces.get(currentPosition);
        return !currentPiece.isCannon();
    }

    private boolean canCatchPiece(Map<Position, Piece> pieces, Position targetPosition, boolean hasPassed) {
        if (!hasPassed) {
            return false;
        }
        Piece targetPiece = pieces.get(targetPosition);
        return !targetPiece.isSameSide(side) && !targetPiece.isCannon();
    }

    @Override
    public boolean isCannon() {
        return true;
    }
}
