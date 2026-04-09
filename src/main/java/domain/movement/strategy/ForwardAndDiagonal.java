package domain.movement.strategy;

import domain.board.Intersection;
import domain.board.Palace;
import domain.movement.MoveAmount;
import domain.movement.Route;
import domain.movement.Vector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ForwardAndDiagonal implements MoveStrategy {

    private final MoveAmount diagonalMovementAmount;
    private final Palace palace = new Palace();

    public ForwardAndDiagonal(MoveAmount diagonalMovementAmount) {
        this.diagonalMovementAmount = diagonalMovementAmount;
    }

    @Override
    public List<Route> getRoutes(
            Intersection from,
            Collection<Vector> allowedVectors
    ) {
        return allowedVectors.stream()
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
        List<Vector> forwardablePalaceVectors = palace.getDiagonalVectors(from)
                .stream()
                .filter(allowedPalaceVectors::contains)
                .toList();

        return getRoutes(from, forwardablePalaceVectors)
                .stream()
                .filter(Route::containsOnlyPalace)
                .toList();
    }

    private List<Route> getRoutesOfSingleVector(
            Intersection from,
            Vector vector
    ) {
        Intersection forwardNode = vector.next(from);
        List<Intersection> leftRoute = moveAsAmount(forwardNode, vector.turnLeft45Degrees());
        List<Intersection> rightRoute = moveAsAmount(forwardNode, vector.turnRight45Degrees());

        return List.of(
                new Route(leftRoute),
                new Route(rightRoute)
        );
    }

    private List<Intersection> moveAsAmount(
            Intersection from,
            Vector vector
    ) {
        List<Intersection> intersections = new ArrayList<>();
        intersections.add(from);

        Intersection currentIntersection = vector.next(from);
        for (int length = 1; diagonalMovementAmount.isGreaterOrEqual(length); length++) {
            intersections.add(currentIntersection);
            currentIntersection = vector.next(currentIntersection);
        }

        return intersections;
    }
}
