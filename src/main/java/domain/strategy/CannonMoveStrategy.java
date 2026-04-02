package domain.strategy;

import domain.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class CannonMoveStrategy extends MoveStrategy {

    private CannonMoveStrategy(Position position) {
        super(position);
    }

    public static CannonMoveStrategy of(Position position) {
        return new CannonMoveStrategy(position);
    }

    @Override
    public boolean isMoveAble(Position destination) {
        return isSameCol(destination) || isSameRow(destination);
    }

    @Override
    public boolean isPathRestricted(Position destination, List<Position> piecePositions) {
        List<Position> route = routePositions(destination);
        return piecePositions.stream().filter(route::contains).count() != 1;
    }

    private List<Position> routePositions(Position destination) {
        if (isSameRow(destination)) {
            return createRoute(destination, position -> nextHorizontal(position, destination));
        }

        if (isSameCol(destination)) {
            return createRoute(destination, position -> nextVertical(position, destination));
        }

        return List.of();
    }

    private List<Position> createRoute(Position destination, UnaryOperator<Position> nextStep) {
        List<Position> route = new ArrayList<>();
        Position current = nextStep.apply(position);
        while (!current.equals(destination)) {
            route.add(current);
            current = nextStep.apply(current);
        }
        return route;
    }

    private Position nextHorizontal(Position current, Position destination) {
        if (current.col() < destination.col()) {
            return current.right();
        }
        return current.left();
    }

    private Position nextVertical(Position current, Position destination) {
        if (current.row() < destination.row()) {
            return current.down();
        }
        return current.up();
    }

    private boolean isSameRow(Position destination) {
        return position.row() == destination.row();
    }

    private boolean isSameCol(Position destination) {
        return position.col() == destination.col();
    }
}
