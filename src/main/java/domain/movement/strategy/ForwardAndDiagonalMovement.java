package domain.movement.strategy;

import domain.board.Intersection;
import domain.movement.MoveAmount;
import domain.movement.Route;
import domain.movement.Vector;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ForwardAndDiagonalMovement {

    public Set<Route> getRoutes(
            Intersection from,
            MoveAmount diagonalMovementAmount,
            Vector vector
    ) {
        Intersection forwardNode = vector.next(from);
        List<Intersection> leftRoute = moveAsAmount(forwardNode, diagonalMovementAmount, vector.turnLeft45Degrees());
        List<Intersection> rightRoute = moveAsAmount(forwardNode, diagonalMovementAmount, vector.turnRight45Degrees());

        return Set.of(
                new Route(leftRoute),
                new Route(rightRoute)
        );
    }

    private List<Intersection> moveAsAmount(
            Intersection from,
            MoveAmount moveAmount,
            Vector vector
    ) {
        List<Intersection> intersections = new ArrayList<>();
        intersections.add(from);

        Intersection currentIntersection = vector.next(from);
        for (int i = 0; i < moveAmount.amount(); i++) {
            intersections.add(currentIntersection);
            currentIntersection = vector.next(currentIntersection);
        }

        return intersections;
    }
}
