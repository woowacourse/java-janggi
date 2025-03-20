package janggi;

import java.util.ArrayList;
import java.util.List;

public class Route {

    public static List<Position> of(final Position departure, final Position destination) {
        int diffRow = destination.subtractRow(departure);
        int diffColumn = destination.subtractColumn(departure);
        List<Position> route = new ArrayList<>();
        route.add(departure);
        int moveCount = Math.max(Math.abs(diffRow), Math.abs(diffColumn));
        for (int i = 0; i < moveCount; i++) {
            decideDirection(route, route.getLast(), destination);
        }
        excludeDepartureAndDestination(route);
        return route;
    }

    private static void excludeDepartureAndDestination(final List<Position> route) {
        route.removeFirst();
        route.removeLast();
    }

    private static void decideDirection(final List<Position> route, final Position current,
                                        final Position destination) {
        int diffRow = destination.subtractRow(current);
        int diffColumn = destination.subtractColumn(current);
        int rowDirection = calculateDirection(diffRow);
        int columnDirection = calculateDirection(diffColumn);
        if (Math.abs(diffRow) > Math.abs(diffColumn)) {
            route.add(current.adjust(rowDirection, 0));
        }
        if (Math.abs(diffRow) < Math.abs(diffColumn)) {
            route.add(current.adjust(0, columnDirection));
        }
        if (Math.abs(diffRow) == Math.abs(diffColumn)) {
            route.add(current.adjust(rowDirection, columnDirection));
        }
    }

    private static int calculateDirection(final int targetDirection) {
        return (int) Math.signum(targetDirection);
    }
}
