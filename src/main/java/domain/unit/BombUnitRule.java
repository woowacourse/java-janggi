package domain.unit;

import domain.Position;
import domain.Route;
import domain.UnitType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BombUnitRule implements UnitRule {
    @Override
    public List<Route> calculateAllRoute(Position start) {
        List<Route> routes = new ArrayList<>();
        List<Position> positions = calculateEndPoints(start);
        for (Position end : positions) {
            Route route = calculateRoute(start, end);
            if (route.getPoints().size() == 1) {
                continue;
            }
            routes.add(route);
        }
        return routes;
    }

    public List<Position> calculateEndPoints(Position start) {
        int x = start.getX();
        int y = start.getY();
        List<Position> xPositions = IntStream.range(0, Position.X_MAX + 1)
                .filter(element -> element != x)
                .mapToObj(element -> new Position(element, y))
                .toList();
        List<Position> yPositions = IntStream.range(0, Position.Y_MAX + 1)
                .filter(element -> element != y)
                .mapToObj(element -> new Position(x, element))
                .toList();
        return Stream.concat(xPositions.stream(), yPositions.stream())
                .toList();
    }

    public Route calculateRoute(Position start, Position end) {
        int startX = start.getX();
        int startY = start.getY();

        int endX = end.getX();
        int endY = end.getY();

        if (startX == endX) {
            int maxY = Integer.max(startY, endY);
            int minY = Integer.min(startY, endY);
            return Route.of(IntStream.range(minY, maxY + 1)
                    .filter(y -> startY != y)
                    .mapToObj(y -> new Position(startX, y))
                    .toList());
        }
        int maxX = Integer.max(startX, endX);
        int minX = Integer.min(startX, endX);
        return Route.of(IntStream.range(minX, maxX + 1)
                .filter(x -> startX != x)
                .mapToObj(x -> new Position(x, startY))
                .toList());
    }

    @Override
    public UnitType getType() {
        return UnitType.BOMB;
    }
}
