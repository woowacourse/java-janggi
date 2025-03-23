package janggi.coordinate;

import java.util.ArrayList;
import java.util.List;

public class Route {

    private final Position departure;
    private final Position destination;

    private Route(final Position departure, final Position destination) {
        this.departure = departure;
        this.destination = destination;
    }

    public static Route of(final Position departure, final Position destination) {
        return new Route(departure, destination);
    }

    public List<Position> calculate() {
        return calculate(true);
    }

    public List<Position> calculate(boolean excludeDestination) {
        List<Position> route = new ArrayList<>();
        route.add(departure);

        Vector origin = departure.vectorTo(destination);
        Vector straightPart = origin.extractStraightForDiagonal();
        Vector diagonalPart = origin.subtract(straightPart);

        for (Vector step : straightPart.splitToUnitVectors()) {
            Position next = route.getLast().add(step);
            route.add(next);
        }

        for (Vector step : diagonalPart.splitToUnitVectors()) {
            Position next = route.getLast().add(step);
            route.add(next);
        }

        excludeDepartureAndDestination(route, excludeDestination);

        return route;
    }

    private void excludeDepartureAndDestination(final List<Position> route, final boolean excludeDestination) {
        route.removeFirst();

        if (route.isEmpty()) {
            return;
        }

        if (excludeDestination) {
            route.removeLast();
        }
    }
}

