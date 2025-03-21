package janggi.domain;

import java.util.ArrayList;
import java.util.List;

public class Route {

    public static List<Position> of(final Position departure, final Position destination) {
        List<Position> route = new ArrayList<>();
        route.add(departure);
        int moveCount = calculateMoveCount(departure, destination);
        for (int i = 0; i < moveCount; i++) {
            decideDirection(route, route.getLast(), destination);
        }
        excludeDepartureAndDestination(route);

        return route;
    }

    private static int calculateMoveCount(final Position departure, final Position destination) {
        return Math.max(Math.abs(destination.subtractRow(departure)), Math.abs(destination.subtractColumn(departure)));
    }

    private static void excludeDepartureAndDestination(final List<Position> route) {
        route.removeFirst();
        route.removeLast();
    }

    private static void decideDirection(final List<Position> route,
                                        final Position current,
                                        final Position destination) {
        int differenceOfRow = destination.subtractRow(current);
        int differenceOfColumn = destination.subtractColumn(current);
        int rowDirection = calculateDirection(differenceOfRow);
        int columnDirection = calculateDirection(differenceOfColumn);
        if (Math.abs(differenceOfRow) > Math.abs(differenceOfColumn)) {
            route.add(current.adjust(rowDirection, 0));
        }
        if (Math.abs(differenceOfRow) < Math.abs(differenceOfColumn)) {
            route.add(current.adjust(0, columnDirection));
        }
        if (Math.abs(differenceOfRow) == Math.abs(differenceOfColumn)) {
            route.add(current.adjust(rowDirection, columnDirection));
        }
    }

    private static int calculateDirection(final int targetDirection) {
        return (int) Math.signum(targetDirection);
    }
}
