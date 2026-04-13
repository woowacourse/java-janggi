package domain.strategy;

import domain.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ChariotMoveStrategy extends MoveStrategy {

    private static final ChariotMoveStrategy INSTANCE = new ChariotMoveStrategy();

    private ChariotMoveStrategy() {
    }

    public static ChariotMoveStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean canMoveTo(Position currentPosition, Position destination) {
        if (isPalaceDiagonalMove(currentPosition, destination)) {
            return true;
        }
        if (isHorizontalMove(currentPosition, destination)) {
            return !isVerticalMove(currentPosition, destination);
        }
        return isVerticalMove(currentPosition, destination);
    }

    private boolean isPalaceDiagonalMove(Position currentPosition, Position destination) {
        if (!isPalaceArea(currentPosition)) {
            return false;
        }
        return palaceReachablePositions(currentPosition).contains(destination);
    }

    private boolean isPalaceArea(Position currentPosition) {
        return palace.isPalaceRedArea(currentPosition) || palace.isPalaceGreenArea(currentPosition);
    }

    private List<Position> palaceReachablePositions(Position currentPosition) {
        List<Position> destinations = new ArrayList<>(palace.reachablePositionsInPalace(currentPosition));
        destinations.addAll(oppositeDiagonalPalacePositions(currentPosition));
        return destinations;
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
            return hasNotOccupiedPositionIn(palace.reachablePositionsInPalace(currentPosition), occupiedPositions);
        }
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
