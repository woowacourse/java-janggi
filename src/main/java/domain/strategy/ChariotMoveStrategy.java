package domain.strategy;

import domain.Position;
import java.util.ArrayList;
import java.util.List;

public class ChariotMoveStrategy extends MoveStrategy {

    @Override
    public boolean canMoveTo(Position currentPosition, Position destination) {
        return isHorizontalMove(currentPosition, destination) || isVerticalMove(currentPosition, destination);
    }

    @Override
    public boolean hasValidPathTo(Position currentPosition, Position destination, List<Position> occupiedPositions) {
        if (isHorizontalMove(currentPosition, destination)) {
            return hasNotOccupiedPositionIn(horizontalRoute(currentPosition, destination), occupiedPositions);
        }

        if (isVerticalMove(currentPosition, destination)) {
            return hasNotOccupiedPositionIn(verticalRouteTo(currentPosition, destination), occupiedPositions);
        }

        return false;
    }

    private boolean isHorizontalMove(Position currentPosition, Position destination) {
        return currentPosition.row() == destination.row();
    }

    private boolean isVerticalMove(Position currentPosition, Position destination) {
        return currentPosition.col() == destination.col();
    }

    private List<Position> horizontalRoute(Position currentPosition, Position destination) {
        List<Position> routePositions = new ArrayList<>();

        int startCol = Math.min(currentPosition.col(), destination.col()) + 1;
        int endCol = Math.max(currentPosition.col(), destination.col());

        for (int col = startCol; col < endCol; col++) {
            routePositions.add(new Position(currentPosition.row(), col));
        }
        return routePositions;
    }

    private List<Position> verticalRouteTo(Position currentPosition, Position destination) {
        List<Position> routePositions = new ArrayList<>();

        int startRow = Math.min(currentPosition.row(), destination.row()) + 1;
        int endRow = Math.max(currentPosition.row(), destination.row());

        for (int row = startRow; row < endRow; row++) {
            routePositions.add(new Position(row, currentPosition.col()));
        }
        return routePositions;
    }

    private boolean hasNotOccupiedPositionIn(List<Position> routePositions, List<Position> occupiedPositions) {
        return occupiedPositions.stream()
                .noneMatch(routePositions::contains);
    }
}
