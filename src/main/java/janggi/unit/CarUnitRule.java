package janggi.unit;

import janggi.position.Position;
import janggi.position.Route;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CarUnitRule implements UnitRule {
    public UnitType getType() {
        return UnitType.CAR;
    }

    @Override
    public List<Route> calculateAllRoute(Position start) {
        List<Route> routes = new ArrayList<>();
        List<Position> positions = calculateEndPoints(start);
        for (Position end : positions) {
            routes.add(calculateRoute(start, end));
        }
        return routes;
    }

    private List<Position> calculateEndPoints(Position start) {
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

    private Route calculateRoute(Position start, Position end) {
        int startX = start.getX();
        int startY = start.getY();

        int endX = end.getX();
        int endY = end.getY();

        if (startX == endX) {
            return calculateYRoute(startY, endY, startX);
        }
        return calculateXRoute(startX, endX, startY);
    }

    private static Route calculateYRoute(int startY, int endY, int startX) {
        if (startY < endY) {
            return Route.of(IntStream.range(startY, endY + 1)
                    .filter(y -> startY != y)
                    .mapToObj(y -> new Position(startX, y))
                    .toList());
        }
        return Route.of(IntStream.range(endY, startY + 1)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .filter(y -> startY != y)
                .map(y -> new Position(startX, y))
                .toList());
    }

    private static Route calculateXRoute(int startX, int endX, int startY) {
        if (startX < endX) {
            return Route.of(IntStream.range(startX, endX + 1)
                    .filter(x -> startX != x)
                    .mapToObj(x -> new Position(x, startY))
                    .toList());
        }
        return Route.of(IntStream.range(endX, startX + 1)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .filter(x -> startX != x)
                .map(x -> new Position(x, startY))
                .toList());
    }
}
