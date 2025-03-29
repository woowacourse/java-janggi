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
        return calculate(true, true);
    }

    public List<Position> calculateWithDestination() {
        return calculate(true, false);
    }

    public List<Position> calculateWithDepartureAndDestination() {
        return calculate(false, false);
    }

    private List<Position> calculate(final boolean shouldExcludeDeparture, final boolean shouldExcludeDestination) {
        final List<Position> route = new ArrayList<>(List.of(departure));

        final Vector origin = departure.vectorTo(destination);
        final Vector straightPart = origin.extractStraightForDiagonal();
        final Vector diagonalPart = origin.subtract(straightPart);

        calculatePart(straightPart, route);
        calculatePart(diagonalPart, route);

        excludeDepartureAndDestination(route, shouldExcludeDeparture, shouldExcludeDestination);

        return route;
    }

    private void calculatePart(final Vector part, final List<Position> route) {
        for (final Vector step : part.splitToUnitVectors()) {
            final Position next = route.getLast().add(step);
            route.add(next);
        }
    }

    private void excludeDepartureAndDestination(final List<Position> route, final boolean shouldExcludeDeparture, final boolean shouldExcludeDestination) {
        if (shouldExcludeDeparture) {
            route.removeFirst();
        }

        if (shouldExcludeDestination && !route.isEmpty()) {
            route.removeLast();
        }
    }
}

