package domain.movement.strategy;

import domain.board.Intersection;
import domain.movement.MoveAmount;
import domain.movement.Route;
import domain.movement.Vector;
import java.util.ArrayList;
import java.util.List;

public class ForwardAndDiagonalMovement implements MoveStrategy {

    private final MoveAmount diagonalMovementAmount;

    public ForwardAndDiagonalMovement(MoveAmount diagonalMovementAmount) {
        this.diagonalMovementAmount = diagonalMovementAmount;
    }

    @Override
    public List<Route> getRoutes(
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
        for (int i = 0; i < diagonalMovementAmount.amount(); i++) {
            intersections.add(currentIntersection);
            currentIntersection = vector.next(currentIntersection);
        }

        return intersections;
    }
}
