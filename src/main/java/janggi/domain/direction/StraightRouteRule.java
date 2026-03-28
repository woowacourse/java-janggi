package janggi.domain.direction;

import static janggi.domain.direction.Direction.BACK;
import static janggi.domain.direction.Direction.FRONT;
import static janggi.domain.direction.Direction.LEFT;
import static janggi.domain.direction.Direction.RIGHT;

import janggi.domain.Location;
import java.util.List;

public class StraightRouteRule implements RouteRule {

    @Override
    public List<Location> calculateRoute(Location from, Location to) {
        int maxDistance = calculateMaxDistance(from, to);

        List<Route> directions = List.of(
                Route.create(FRONT, maxDistance),
                Route.create(BACK, maxDistance),
                Route.create(LEFT, maxDistance),
                Route.create(RIGHT, maxDistance)
        );

        for(Route route : directions) {
            List<Location> locations = route.apply(from);
            if(locations.contains(to)) {
                return locations;
            }
        }

        throw new IllegalArgumentException("해당 위치에 도달할 수 없습니다.");
    }

    private int calculateMaxDistance(Location from, Location to) {
        int horizontalDiff = Math.abs(from.calculateHorizontalDiff(to));
        int verticalDiff = Math.abs(from.calculateVerticalDiff(to));
        return Math.max(horizontalDiff, verticalDiff);
    }
}
