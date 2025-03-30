package janggi.domain.piece;

import janggi.domain.Side;
import janggi.domain.movement.Direction;
import janggi.domain.movement.PalaceMovement;
import janggi.domain.movement.Position;
import janggi.domain.movement.Vector;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Soldier extends Piece {

    private static final List<Direction> MOVEMENT_DIRECTIONS = List.of(Direction.DOWN, Direction.LEFT, Direction.RIGHT);

    public Soldier(Side side) {
        super(side);
    }

    @Override
    public Set<Position> generateAvailableMovePositions(Map<Position, Piece> pieces, Position currentPosition) {
        Set<Position> availableMovePositions = MOVEMENT_DIRECTIONS.stream()
                .map(Direction::getVector)
                .map(vector -> currentPosition.calculateNextPosition(vector.side(side)))
                .flatMap(Optional::stream)
                .filter(availablePosition -> canMoveToPosition(pieces, availablePosition))
                .collect(Collectors.toSet());
        if (PalaceMovement.isInsidePalace(currentPosition)) {
            Set<Position> palaceMovePositions = generatePalaceMovePositions(pieces, currentPosition);
            availableMovePositions.addAll(palaceMovePositions);
        }
        return availableMovePositions;
    }

    private boolean canMoveToPosition(Map<Position, Piece> pieces, Position position) {
        if (!pieces.containsKey(position)) {
            return true;
        }
        Piece nextPiece = pieces.get(position);
        return !nextPiece.isSameSide(side);
    }

    private boolean isCorrectDirection(Position currentPosition, Position targetPosition) {
        Vector currentDirection = Position.getVerticalVector(currentPosition, targetPosition);
        if (side == Side.CHO) {
            return Direction.UP.getVector().equals(currentDirection);
        }
        return Direction.DOWN.getVector().equals(currentDirection);
    }

    private Set<Position> generatePalaceMovePositions(Map<Position, Piece> pieces, Position currentPosition) {
        List<Direction> palaceDirections = PalaceMovement.getDirectionsAtPosition(currentPosition);
        return palaceDirections.stream().map(Direction::getVector)
                .map(currentPosition::calculateNextPosition)
                .flatMap(Optional::stream)
                .filter(availablePosition -> isCorrectDirection(currentPosition, availablePosition))
                .filter(availablePosition -> canMoveToPosition(pieces, availablePosition))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public double getPoints() {
        return 2;
    }
}
