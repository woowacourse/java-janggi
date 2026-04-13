package domain.strategy;

import domain.Position;
import java.util.ArrayList;
import java.util.List;

public class GreenSoldierMoveStrategy extends MoveStrategy {

    @Override
    public boolean canMoveTo(Position currentPosition, Position destination) {
        if (isPalaceArea(currentPosition)) {
            return movablePositionsInPalace(currentPosition).contains(destination);
        }
        return basicMovablePositions(currentPosition).contains(destination);
    }

    @Override
    public boolean hasValidPathTo(Position currentPosition, Position destination, List<Position> occupiedPositions) {
        return true;
    }

    private boolean isPalaceArea(Position currentPosition) {
        return palace.isPalaceRedArea(currentPosition);
    }

    private List<Position> movablePositionsInPalace(Position currentPosition) {
        List<Position> destinations = new ArrayList<>(basicMovablePositions(currentPosition));
        List<Position> reachablePalacePositions = palace.reachablePositionsInPalace(currentPosition).stream()
                .filter(destination -> !isRowIncreasingDiagonalPosition(currentPosition, destination)).toList();
        destinations.addAll(reachablePalacePositions);
        return destinations;
    }

    private List<Position> basicMovablePositions(Position currentPosition) {
        return List.of(
                currentPosition.right(),
                currentPosition.up(),
                currentPosition.left()
        );
    }

    private boolean isRowIncreasingDiagonalPosition(Position currentPosition, Position destination) {
        return List.of(
                currentPosition.downCrossLeft(),
                currentPosition.downCrossRight())
                .contains(destination);
    }
}
