package janggi.piece.pieces.moverule;

import janggi.position.Direction;
import janggi.position.Position;
import janggi.position.Route;
import java.util.ArrayList;
import java.util.List;

public class StraightDiagonalRule implements MoveRule {
    private final int maxDepth;

    public StraightDiagonalRule(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    @Override
    public List<Route> moveAll(Position start) {
        List<Route> routes = new ArrayList<>();

        findPath(0, Direction.NONE, new ArrayList<>(), start, routes);
        return routes;
    }

    private void findPath(int depth, Direction before, List<Position> route, Position prevPoint, List<Route> routes) {
        if (depth == maxDepth) {
            routes.add(Route.of(route.stream().toList()));
            return;
        }
        for (Direction direction : before.getNextWithDiagonal()) {
            move(depth, route, prevPoint, routes, direction);
        }
    }

    private void move(int depth, List<Position> route, Position prevPoint, List<Route> routes,
                      Direction direction) {
        if (!Position.isCanBePosition(prevPoint.getColumn() + direction.getX(),
                prevPoint.getRow() + direction.getY())) {
            return;
        }
        Position next = new Position(prevPoint.getColumn() + direction.getX(),
                prevPoint.getRow() + direction.getY());
        route.add(next);
        findPath(depth + 1, direction, route, next, routes);
        route.remove(next);
    }
}
