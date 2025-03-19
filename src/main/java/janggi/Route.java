package janggi;

import java.util.ArrayList;
import java.util.List;

public class Route {

    public static List<Position> decideRoute(Position departure, Position destination) {
        int diffRow = destination.subtractRow(departure);
        int diffColumn = destination.subtractColumn(departure);
        List<Position> route = new ArrayList<>();
        route.add(departure);
        for (int i = 0; i < Math.max(Math.abs(diffRow), Math.abs(diffColumn)); i++) {
            decideDirection(route, route.getLast(), destination);
        }
        route.removeFirst();
        return route;
    }

    private static void decideDirection(List<Position> route, Position current, Position destination) {
        int diffRow = destination.subtractRow(current);
        int diffColumn = destination.subtractColumn(current);
        int rowDirection = calculateDirection(diffRow);
        int columnDirection = calculateDirection(diffColumn);
        if (Math.abs(diffRow) > Math.abs(diffColumn)) {
            route.add(move(route.getLast(), rowDirection, 0));
        }
        if (Math.abs(diffRow) < Math.abs(diffColumn)) {
            route.add(move(route.getLast(), 0, columnDirection));
        }
        if (Math.abs(diffRow) == Math.abs(diffColumn)) {
            route.add(move(route.getLast(), rowDirection, columnDirection));
        }
    }

    private static int calculateDirection(final int targetDirection) {
        return (int) Math.signum(targetDirection);
    }

    private static Position move(Position current, int rowDirection, int columnDirection) {
        return Position.of(current.getRow() + rowDirection, current.getColumn() + columnDirection);
    }
}
