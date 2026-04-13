package domain.strategy;

import domain.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CannonMoveStrategy extends MoveStrategy {

    private static final CannonMoveStrategy INSTANCE = new CannonMoveStrategy();

    private CannonMoveStrategy() {
    }

    public static CannonMoveStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean canMoveTo(Position currentPosition, Position destination) {
        if (isPalaceDiagonalMove(currentPosition, destination)) {
            return true;
        }
        if (isHorizontalMove(currentPosition, destination)) {
            return isNotAdjacentHorizontalMove(destination, currentPosition);
        }
        if (isVerticalMove(currentPosition, destination)) {
            return isNotAdjacentVerticalMove(destination, currentPosition);
        }

        return false;
    }

    private boolean isPalaceDiagonalMove(Position currentPosition, Position destination) {
        if (!isPalaceArea(currentPosition)) {
            return false;
        }

        return oppositeDiagonalPalacePositions(currentPosition).contains(destination);
    }

    private boolean isPalaceArea(Position currentPosition) {
        return palace.isPalaceRedArea(currentPosition) || palace.isPalaceGreenArea(currentPosition);
    }

    private List<Position> oppositeDiagonalPalacePositions(Position currentPosition) {
        return Stream.of(
                        currentPosition.downCrossRight().downCrossRight(),
                        currentPosition.downCrossLeft().downCrossLeft(),
                        currentPosition.upCrossRight().upCrossRight(),
                        currentPosition.upCrossLeft().upCrossLeft()
                )
                .filter(this::isPalaceArea)
                .toList();
    }

    @Override
    public boolean hasValidPathTo(Position currentPosition, Position destination, List<Position> occupiedPositions) {
        if (isPalaceDiagonalMove(currentPosition, destination)) {
            return doesHaveExactlyOnePieceInPath(palace.reachablePositionsInPalace(currentPosition), occupiedPositions);
        }
        if (isHorizontalMove(currentPosition, destination)) {
            return doesHaveExactlyOnePieceInPath(horizontalRoute(currentPosition, destination), occupiedPositions);
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

    private boolean doesHaveExactlyOnePieceInPath(List<Position> routePositions, List<Position> occupiedPositions) {
        return occupiedPositions.stream()
                .filter(routePositions::contains)
                .count() == 1;
    }
}
