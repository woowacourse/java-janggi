package domain.strategy;

import domain.Position;
import java.util.ArrayList;
import java.util.List;

public class CannonMoveStrategy extends MoveStrategy {

    public CannonMoveStrategy(Position position) {
        super(position);
    }

    public static CannonMoveStrategy of(Position position) {
        return new CannonMoveStrategy(position);
    }

    @Override
    public void updateRoute() {
    }

    @Override
    public boolean canMoveTo(Position destination) {
        if (isHorizontalMove(destination)) {
            return isNotAdjacentHorizontalMove(destination);
        }

        if (isVerticalMove(destination)) {
            return isNotAdjacentVerticalMove(destination);
        }
        return false;
    }

    @Override
    public boolean hasValidPathTo(Position destination, List<Position> occupiedPositions) {
        if (isHorizontalMove(destination)) {
            return doesHaveExactlyOnePieceInPath(horizontalRoute(destination), occupiedPositions);
        }

        if (isVerticalMove(destination)) {
            return doesHaveExactlyOnePieceInPath(verticalRouteTo(destination), occupiedPositions);
        }

        return false;
    }

    private boolean isHorizontalMove(Position destination) {
        return position().row() == destination.row();
    }

    private boolean isVerticalMove(Position destination) {
        return position().col() == destination.col();
    }

    private boolean isNotAdjacentHorizontalMove(Position destination) {
        return !destination.equals(position().left()) && !destination.equals(position().right());
    }

    private boolean isNotAdjacentVerticalMove(Position destination) {
        return !destination.equals(position().up()) && !destination.equals(position().down());
    }

    private List<Position> horizontalRoute(Position destination) {
        List<Position> routePositons = new ArrayList<>();

        int startCol = Math.min(position().col(), destination.col()) + 1;
        int endCol = Math.max(position().col(), destination.col());

        for (int col = startCol; col < endCol; col++) {
            routePositons.add(new Position(position().row(), col));
        }
        return routePositons;
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

    private boolean doesHaveExactlyOnePieceInPath(List<Position> routePositions, List<Position> occupiedPositions) {
        return occupiedPositions.stream()
                .filter(routePositions::contains)
                .count() == 1;
    }
}
