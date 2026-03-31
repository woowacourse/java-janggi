package domain.strategy;

import domain.Position;
import java.util.ArrayList;
import java.util.List;

public class CannonMoveStrategy extends MoveStrategy {

    @Override
    public boolean canMoveTo(Position currentPosition, Position destination) {
        if (isHorizontalMove(currentPosition, destination)) {
            return isNotAdjacentHorizontalMove(destination, currentPosition);
        }

        if (isVerticalMove(currentPosition, destination)) {
            return isNotAdjacentVerticalMove(destination, currentPosition);
        }
        return false;
    }

    @Override
    public boolean hasValidPathTo(Position currentPosition, Position destination, List<Position> occupiedPositions) {
        if (isHorizontalMove(currentPosition, destination)) {
            return doesHaveExactlyOnePieceInPath(horizontalRoute(destination, currentPosition), occupiedPositions);
        }

        if (isVerticalMove(currentPosition, destination)) {
            return doesHaveExactlyOnePieceInPath(verticalRouteTo(currentPosition, destination), occupiedPositions);
        }

        return false;
    }

    private boolean isHorizontalMove(Position currentPosition, Position destination) {
        return currentPosition.row() == destination.row();
    }

    private boolean isVerticalMove(Position currentPosition, Position destination) {
        return currentPosition.col() == destination.col();
    }

    private boolean isNotAdjacentHorizontalMove(Position currentPosition, Position destination) {
        return !destination.equals(currentPosition.left()) && !destination.equals(currentPosition.right());
    }

    private boolean isNotAdjacentVerticalMove(Position currentPosition, Position destination) {
        return !destination.equals(currentPosition.up()) && !destination.equals(currentPosition.down());
    }

    private List<Position> horizontalRoute(Position currentPosition, Position destination) {
        List<Position> routePositons = new ArrayList<>();

        int startCol = Math.min(currentPosition.col(), destination.col()) + 1;
        int endCol = Math.max(currentPosition.col(), destination.col());

        for (int col = startCol; col < endCol; col++) {
            routePositons.add(new Position(currentPosition.row(), col));
        }
        return routePositons;
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

    private boolean doesHaveExactlyOnePieceInPath(List<Position> routePositions, List<Position> occupiedPositions) {
        return occupiedPositions.stream()
                .filter(routePositions::contains)
                .count() == 1;
    }
}
