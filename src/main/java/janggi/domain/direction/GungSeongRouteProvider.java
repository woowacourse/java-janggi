package janggi.domain.direction;

import static janggi.domain.direction.Direction.BACK;
import static janggi.domain.direction.Direction.BACK_LEFT;
import static janggi.domain.direction.Direction.BACK_RIGHT;
import static janggi.domain.direction.Direction.FRONT;
import static janggi.domain.direction.Direction.FRONT_LEFT;
import static janggi.domain.direction.Direction.FRONT_RIGHT;
import static janggi.domain.direction.Direction.LEFT;
import static janggi.domain.direction.Direction.RIGHT;

import janggi.domain.Location;
import java.util.List;

public class GungSeongRouteProvider implements RouteProvider {

    @Override
    public List<Location> calculateRoute(Location from, Location to) {
        List<Route> directions = List.of(
                Route.of(List.of(FRONT)),
                Route.of(List.of(LEFT)),
                Route.of(List.of(RIGHT)),
                Route.of(List.of(BACK)),
                Route.of(List.of(FRONT_LEFT)),
                Route.of(List.of(FRONT_RIGHT)),
                Route.of(List.of(BACK_LEFT)),
                Route.of(List.of(BACK_RIGHT))
        );

        for(Route route : directions) {
            List<Location> locations = route.apply(from);
            if(locations.getLast().equals(to)) {
                return locations;
            }
        }

        throw new IllegalArgumentException("해당 위치에 도달할 수 없습니다.");
    }
}
