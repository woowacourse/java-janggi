package domain.movement.strategy;

import domain.board.Intersection;
import domain.board.Palace;
import domain.movement.MoveAmount;
import domain.movement.Route;
import domain.movement.Vector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Straight implements MoveStrategy {

    private final MoveAmount moveAmount;
    private final Palace palace = new Palace();

    public Straight(MoveAmount moveAmount) {
        this.moveAmount = moveAmount;
    }

    @Override
    public List<Route> getRoutes(
            Intersection from,
            Collection<Vector> vectors
    ) {
        return vectors.stream()
                .map(vector -> getRoutesOfSingleVector(from, vector))
                .flatMap(List::stream)
                .toList();
    }

    @Override
    public List<Route> getCardinalRoutes(Intersection from) {
        return getRoutes(from, Vector.cardinals());
    }

    @Override
    public List<Route> getPalaceRoutes(Intersection from) {
        return getRoutes(from, palace.getDiagonalVectors(from))
                .stream()
                .filter(Route::containsOnlyPalace)
                .toList();
    }

    @Override
    public List<Route> getPalaceRoutes(Intersection from, Collection<Vector> allowedPalaceVectors) {
        return getRoutes(from, allowedPalaceVectors)
                .stream()
                .filter(Route::containsOnlyPalace)
                .toList();
    }

    private List<Route> getRoutesOfSingleVector(
            Intersection from,
            Vector vector
    ) {
        List<Route> routes = new ArrayList<>();

        for (int length = 1; moveAmount.isGreaterOrEqual(length) && isInBoard(from, length, vector); length++) {
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

        Intersection routeNode = vector.next(from);
        for (int i = 0; i < length; i++) {
            route.add(routeNode);
            routeNode = vector.next(routeNode);
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
