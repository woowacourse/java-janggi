package domain.unit;

import domain.Route;
import domain.UnitType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BombUnitRule implements UnitRule {
    @Override
    public List<Route> calculateAllRoute(Point start) {
        List<Route> routes = new ArrayList<>();
        List<Point> points = calculateEndPoints(start);
        for (Point end : points) {
            Route route = calculateRoute(start, end);
            if (route.getPoints().size() == 1) {
                continue;
            }
            routes.add(route);
        }
        return routes;
    }

    public List<Point> calculateEndPoints(Point start) {
        int x = start.getX();
        int y = start.getY();
        List<Point> xPoints = IntStream.range(0, Point.X_MAX + 1)
                .filter(element -> element != x)
                .mapToObj(element -> new Point(element, y))
                .toList();
        List<Point> yPoints = IntStream.range(0, Point.Y_MAX + 1)
                .filter(element -> element != y)
                .mapToObj(element -> new Point(x, element))
                .toList();
        return Stream.concat(xPoints.stream(), yPoints.stream())
                .toList();
    }

    public Route calculateRoute(Point start, Point end) {
        int startX = start.getX();
        int startY = start.getY();

        int endX = end.getX();
        int endY = end.getY();

        if (startX == endX) {
            int maxY = Integer.max(startY, endY);
            int minY = Integer.min(startY, endY);
            return Route.of(IntStream.range(minY, maxY + 1)
                    .filter(y -> startY != y)
                    .mapToObj(y -> new Point(startX, y))
                    .toList());
        }
        int maxX = Integer.max(startX, endX);
        int minX = Integer.min(startX, endX);
        return Route.of(IntStream.range(minX, maxX + 1)
                .filter(x -> startX != x)
                .mapToObj(x -> new Point(x, startY))
                .toList());
    }

    @Override
    public UnitType getType() {
        return UnitType.BOMB;
    }
}
