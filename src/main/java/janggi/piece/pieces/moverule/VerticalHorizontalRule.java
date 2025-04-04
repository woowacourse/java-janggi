package janggi.piece.pieces.moverule;

import janggi.board.Palace;
import janggi.position.Direction;
import janggi.position.Position;
import janggi.position.Route;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class VerticalHorizontalRule implements MoveRule {
    @Override
    public List<Route> moveAll(Position start) {
        List<Position> positions = calculateEndPoints(start);

        List<Route> straightRoutes = positions.stream()
                .map(end -> calculateStraightRoute(start, end))
                .toList();

        List<Route> routesInPalace = calculateRoutesInPalace(start);
        return Stream.concat(straightRoutes.stream(), routesInPalace.stream()).toList();
    }

    private List<Route> calculateRoutesInPalace(Position start) {
        List<Route> routesInPalace = new ArrayList<>();
        if (Palace.canDiagonalInPalace(start)) {
            routesInPalace = calculateRouteInPalace(start);
        }
        return routesInPalace;
    }

    private List<Position> calculateEndPoints(Position start) {
        List<Position> parallelPositions = start.createParallelPosition(0, Position.COLUMN_MAX);
        List<Position> verticalPositions = start.createVerticalPosition(0, Position.ROW_MAX);
        return Stream.concat(parallelPositions.stream(), verticalPositions.stream())
                .toList();
    }

    private Route calculateStraightRoute(Position start, Position end) {
        if (start.isParallel(end)) {
            return Route.of(start.createParallelPosition(start.getColumn(), end.getColumn()));
        }
        return Route.of(start.createVerticalPosition(start.getRow(), end.getRow()));
    }

    private List<Route> calculateRouteInPalace(Position start) {
        List<Route> routes = new ArrayList<>();

        for (Direction direction : Direction.getDiagonal()) {
            List<Position> positions = new ArrayList<>();
            createDiagonalRoute(direction, start, positions, routes);

        }
        return routes;
    }

    private void createDiagonalRoute(Direction direction, Position position, List<Position> positions,
                                     List<Route> routes) {
        if (Position.isCanBePosition(position.getColumn() + direction.getX(),
                position.getRow() + direction.getY())) {
            Position next = position.move(direction);

            createRouteInPalace(direction, position, positions, routes, next);
        }
    }

    private void createRouteInPalace(Direction direction, Position position, List<Position> positions,
                                     List<Route> routes,
                                     Position next) {
        while (Palace.isInPalace(next) && !next.equals(position)) {
            positions.add(next);
            routes.add(Route.of(new ArrayList<>(positions)));
            position = next;
            if (Position.isCanBePosition(position.getColumn() + direction.getX(),
                    position.getRow() + direction.getY())) {
                next = position.move(direction);
                continue;
            }
            break;
        }
    }
}
