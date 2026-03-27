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
    public void updateRoute() {}

    @Override
    public boolean isMoveAble(Position destination) {
        if (position.row() == destination.row()) {
            return true;
        }
        if (position.col() == destination.col()) {
            return true;
        }

        return false;
    }

    @Override
    public boolean hasPieceOnPath(Position destination, List<Position> piecePositions) {
        if (position.row() == destination.row()) {
            List<Position> routePositons = getLeftOrRightRoutePositions(destination);
            return piecePositions.stream().anyMatch(routePositons::contains);
        }
        if (position.col() == destination.col()) {
            List<Position> routePositons = getUpOrDownRoutePositions(destination);
            return piecePositions.stream().anyMatch(routePositons::contains);
        }

        return false;
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
