package janggi.domain.piece;

import janggi.domain.Direction;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.Vector;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Chariot extends Piece {

    private static final List<Direction> MOVEMENT_DIRECTIONS = List.of(Direction.UP, Direction.LEFT, Direction.RIGHT, Direction.DOWN);

    public Chariot(Side side) {
        super(side);
    }

    @Override
    public Set<Position> generateAvailableMovePositions(Map<Position, Piece> pieces, Position currentPosition) {
        Set<Position> result = new HashSet<>();
        for (Direction direction : MOVEMENT_DIRECTIONS) {
            Vector vector = direction.getVector();
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
