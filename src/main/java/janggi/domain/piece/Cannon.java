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

public class Cannon extends Piece {

    private static final List<Direction> MOVEMENT_DIRECTIONS = List.of(
            Direction.DOWN,
            Direction.LEFT,
            Direction.RIGHT,
            Direction.UP
    );

    public Cannon(Side side) {
        super(side);
    }

    @Override
    public Set<Position> generateAvailableMovePositions(Map<Position, Piece> pieces, Position currentPosition) {
        Set<Position> result = new HashSet<>();
        for (Direction direction : MOVEMENT_DIRECTIONS) {
            Vector vector = direction.getVector();
            currentPosition.calculateNextPosition(vector)
                    .ifPresent(nextPosition -> searchAvailableMoves(result, pieces, nextPosition, vector, pieces.containsKey(nextPosition)));
        }
        if (PalaceMovement.isInsidePalace(currentPosition) && PalaceMovement.isCorner(currentPosition)) {
            Set<Position> palaceMovePositions = generatePalaceMovePositions(pieces, currentPosition);
            result.addAll(palaceMovePositions);
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
        if (!pieces.containsKey(midPosition) || pieces.get(midPosition).isCannon()) {
            return;
        }
        Position finalPosition = midPosition.moveToNextPosition(vector);
        if (pieces.containsKey(finalPosition)) {
            Piece finalPiece = pieces.get(finalPosition);
            if (finalPiece.isCannon() || finalPiece.isSameSide(side)) {
                return;
            }
        }
        result.add(finalPosition);
    }

    @Override
    public boolean isCannon() {
        return true;
    }

    @Override
    public double getPoints() {
        return 7;
    }
}
