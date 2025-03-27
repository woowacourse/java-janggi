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

    public List<Position> calculateWithDeparture() {
        return calculate(false, true);
    }

    public List<Position> calculateWithDestination() {
        return calculate(true, false);
    }

    public List<Position> calculateWithDepartureAndDestination() {
        return calculate(false, false);
    }

    private List<Position> calculate(boolean shouldExcludeDeparture, boolean shouldExcludeDestination) {
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

        excludeDepartureAndDestination(route, shouldExcludeDeparture, shouldExcludeDestination);

        return route;
    }

    private void excludeDepartureAndDestination(final List<Position> route, final boolean shouldExcludeDeparture, final boolean shouldExcludeDestination) {
        if (shouldExcludeDeparture && !route.isEmpty()) {
            route.removeFirst();
        }

        if (shouldExcludeDestination && !route.isEmpty()) {
            route.removeLast();
        }
    }
}

