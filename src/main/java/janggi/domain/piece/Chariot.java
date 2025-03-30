package janggi.domain.piece;

import janggi.domain.Side;
import janggi.domain.movement.Direction;
import janggi.domain.movement.PalaceMovement;
import janggi.domain.movement.Position;
import janggi.domain.movement.Vector;
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
            currentPosition.calculateNextPosition(vector).ifPresent(movePosition -> searchAvailableMoves(result, pieces, movePosition, vector));
        }
        if (PalaceMovement.isInsidePalace(currentPosition) && PalaceMovement.isCorner(currentPosition)) {
            Set<Position> palaceMovePositions = generatePalaceMovePositions(pieces, currentPosition);
            result.addAll(palaceMovePositions);
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

    private Set<Position> generatePalaceMovePositions(Map<Position, Piece> pieces, Position currentPosition) {
        List<Direction> allDirections = PalaceMovement.getDirectionsAtPosition(currentPosition);
        List<Direction> diagonalDirections = allDirections.stream().filter(Direction::isDiagonal).toList();
        Set<Position> availableMovePositions = new HashSet<>();

        for (Direction diagonalDirection : diagonalDirections) {
            searchAvailablePalaceMoves(availableMovePositions, pieces, currentPosition, diagonalDirection.getVector());
        }

        return availableMovePositions;
    }

    private void searchAvailablePalaceMoves(Set<Position> result, Map<Position, Piece> pieces, Position currentPosition, Vector vector) {
        if (currentPosition.canNotMove(vector)) {
            return;
        }
        Position midPosition = currentPosition.moveToNextPosition(vector);
        if (pieces.containsKey(midPosition) && pieces.get(midPosition).isSameSide(side)) {
            return;
        }
        result.add(midPosition);
        Position finalPosition = midPosition.moveToNextPosition(vector);
        if (pieces.containsKey(finalPosition)) {
            Piece finalPiece = pieces.get(finalPosition);
            if (finalPiece.isSameSide(side)) {
                return;
            }
        }
        result.add(finalPosition);
    }

    @Override
    public double getPoints() {
        return 13;
    }
}
