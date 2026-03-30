package domain.strategy;

import domain.Position;
import java.util.ArrayList;
import java.util.List;

public class ChariotMoveStrategy extends MoveStrategy {

    private ChariotMoveStrategy(Position position) {
        super(position);
    }

    public static ChariotMoveStrategy of(Position position) {
        return new ChariotMoveStrategy(position);
    }

    @Override
    public void updateRoute() {
    }

    @Override
    public boolean canMoveTo(Position destination) {
        return isHorizontalMove(destination) || isVerticalMove(destination);
    }

    @Override
    public boolean hasValidPathTo(Position destination, List<Position> occupiedPositions) {
        if (isHorizontalMove(destination)) {
            return hasNotOccupiedPositionIn(horizontalRoute(destination), occupiedPositions);
        }

        if (isVerticalMove(destination)) {
            return hasNotOccupiedPositionIn(verticalRouteTo(destination), occupiedPositions);
        }

        return false;
    }

    private boolean isHorizontalMove(Position destination) {
        return position().row() == destination.row();
    }

    private boolean isVerticalMove(Position destination) {
        return position().col() == destination.col();
    }

    private List<Position> horizontalRoute(Position destination) {
        List<Position> routePositions = new ArrayList<>();

        int startCol = Math.min(position().col(), destination.col()) + 1;
        int endCol = Math.max(position().col(), destination.col());

        for (int col = startCol; col < endCol; col++) {
            routePositions.add(new Position(position().row(), col));
        }
        return routePositions;
    }

    private List<Position> verticalRouteTo(Position destination) {
        List<Position> routePositions = new ArrayList<>();

        int startRow = Math.min(position().row(), destination.row()) + 1;
        int endRow = Math.max(position().row(), destination.row());

        for (int row = startRow; row < endRow; row++) {
            routePositions.add(new Position(row, position().col()));
        }
        return routePositions;
    }

    private boolean hasNotOccupiedPositionIn(List<Position> routePositions, List<Position> occupiedPositions) {
        return occupiedPositions.stream()
                .noneMatch(routePositions::contains);
    }
}
