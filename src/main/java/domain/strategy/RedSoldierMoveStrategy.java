package domain.strategy;

import domain.Position;
import java.util.ArrayList;
import java.util.List;

public class RedSoldierMoveStrategy extends MoveStrategy {

    @Override
    public boolean canMoveTo(Position currentPosition, Position destination) {
        if (isPalaceArea(currentPosition)) {
            return movablePositionsInPalace(currentPosition).contains(destination);
        }
        return basicMovablePositions(currentPosition).contains(destination);
    }

    @Override
    public boolean hasValidPathTo(Position currentPosition, Position targetPosition, List<Position> occupiedPositions) {
        return true;
    }

    private boolean isPalaceArea(Position currentPosition) {
        return palace.isPalaceGreenArea(currentPosition);
    }

    private List<Position> movablePositionsInPalace(Position currentPosition) {
        List<Position> destinations = new ArrayList<>(basicMovablePositions(currentPosition));
        List<Position> reachablePalacePositions = palace.reachablePositionsInPalace(currentPosition).stream()
                .filter(destination -> !isRowDecreasingDiagonalPosition(currentPosition, destination)).toList();
        destinations.addAll(reachablePalacePositions);
        return destinations;
    }

    private List<Position> basicMovablePositions(Position currentPosition) {
        return List.of(
                currentPosition.right(),
                currentPosition.down(),
                currentPosition.left()
        );
    }

    private boolean isRowDecreasingDiagonalPosition(Position currentPosition, Position destination) {
        return List.of(
                        currentPosition.upCrossLeft(),
                        currentPosition.upCrossRight())
                .contains(destination);
    }
}
