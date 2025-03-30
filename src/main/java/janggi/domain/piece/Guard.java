package janggi.domain.piece;

import janggi.domain.Side;
import janggi.domain.movement.Direction;
import janggi.domain.movement.PalaceMovement;
import janggi.domain.movement.Position;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Guard extends Piece {

    private static final List<Direction> MOVEMENT_DIRECTIONS = List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT);

    public Guard(Side side) {
        super(side);
    }

    @Override
    public Set<Position> generateAvailableMovePositions(Map<Position, Piece> pieces, Position currentPosition) {
        Set<Position> availableMovePositions = MOVEMENT_DIRECTIONS.stream()
                .map(Direction::getVector)
                .map(vector -> currentPosition.calculateNextPosition(vector.side(side)))
                .flatMap(Optional::stream)
                .filter(availablePosition -> canMoveToPosition(pieces, availablePosition))
                .filter(PalaceMovement::isInsidePalace)
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

    private Set<Position> generatePalaceMovePositions(Map<Position, Piece> pieces, Position currentPosition) {
        List<Direction> palaceDirections = PalaceMovement.getDirectionsAtPosition(currentPosition);
        return palaceDirections.stream().map(Direction::getVector)
                .map(currentPosition::calculateNextPosition)
                .flatMap(Optional::stream)
                .filter(availablePosition -> canMoveToPosition(pieces, availablePosition))
                .filter(PalaceMovement::isInsidePalace)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public double getPoints() {
        return 3;
    }
}
