package domain.movement.strategy;

import domain.board.Intersection;
import domain.movement.MoveAmount;
import domain.movement.Route;
import domain.movement.Vector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StraightMovement {

    public List<Route> getRoutes(
            Intersection from,
            MoveAmount moveAmount,
            Vector vector
    ) {
        List<Route> routes = new ArrayList<>();

        for (int length = 1; length <= moveAmount.amount() && isInBoard(from, length, vector); length++) {
            routes.add(createLinearRoute(from, length, vector));
        }

        return Collections.unmodifiableList(routes);
    }

    private Route createLinearRoute(
            Intersection from,
            int length,
            Vector vector
    ) {
        List<Intersection> route = new ArrayList<>();

        Intersection routeNode = from;
        for (int i = 0; i < length && routeNode.isInBoard(); i++) {
            routeNode = vector.next(routeNode);
            route.add(routeNode);
        }

        return new Route(route);
    }

    private boolean isInBoard(Intersection from, int moveAmount, Vector vector) {
        Intersection destination = from;
        for (int i = 0; i < moveAmount; i++) {
            destination = vector.next(destination);
        }

        return destination.isInBoard();
    }
}
