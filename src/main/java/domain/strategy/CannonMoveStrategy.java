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
    public boolean isMoveAble(Position destination) {
        if (position.row() == destination.row()) {
            return !destination.equals(position.left()) && !destination.equals(position.right());
        }
        if (position.col() == destination.col()) {
            return !destination.equals(position.up()) && !destination.equals(position.down());
        }
        return false;
    }

    @Override
    public boolean isInvalidPath(Position destination, List<Position> piecePositions) {
        if (position.row() == destination.row()) {
            List<Position> routePositions = getLeftOrRightRoutePositions(destination);
            return piecePositions.stream().filter(routePositions::contains).count() != 1;
        }

        if (position.col() == destination.col()) {
            List<Position> routePositions = getUpOrDownRoutePositions(destination);
            return piecePositions.stream().filter(routePositions::contains).count() != 1;
        }

        return true;
    }

    private List<Position> getLeftOrRightRoutePositions(Position destination) {

        List<Position> routePositons;
        if (position.col() < destination.col()) {
            routePositons = new ArrayList<>();
            for (int i = position.col() + 1; i < destination.col(); i++) {
                routePositons.add(Position.of(position.row(), i));
            }
            return routePositons;
        }

        routePositons = new ArrayList<>();
        for (int i = destination.col() + 1; i < position.col(); i++) {
            routePositons.add(Position.of(position.row(), i));
        }
        return routePositons;
    }

    private List<Position> getUpOrDownRoutePositions(Position destination) {
        if (position.row() < destination.row()) {
            List<Position> routePositons = new ArrayList<>();
            for (int i = position.row() + 1; i < destination.row(); i++) {
                routePositons.add(Position.of(i, position.col()));
            }
            return routePositons;
        }

        List<Position> routePositons = new ArrayList<>();
        for (int i = destination.row() + 1; i < position.row(); i++) {
            routePositons.add(Position.of(i, position.col()));
        }
        return routePositons;
    }
}
