package domain.piece.route;

import domain.MovingPattern;
import domain.position.JanggiPosition;
import janggiexception.InvalidPathException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Route {

    private final List<MovingPattern> route;

    public Route(final MovingPattern... route) {
        this.route = List.of(route);
    }

    private Route(final List<MovingPattern> route) {
        this.route = route;
    }

    public static Route createEmptyRoute() {
        return new Route(List.of());
    }

    public boolean isSameDirectionWith(final JanggiPosition origin, final JanggiPosition destination) {
        MovingPattern direction = route.getFirst();

        JanggiPosition newPosition = origin;
        while (newPosition.canMoveOnePositionTo(direction)) {
            newPosition = newPosition.moveOnePosition(direction);
            if (newPosition.equals(destination)) {
                return true;
            }
        }
        return false;
    }

    public int getMoveCount(final JanggiPosition origin, final JanggiPosition destination) {
        MovingPattern direction = route.getFirst();

        int count = 0;
        JanggiPosition newPosition = origin;
        while (newPosition.canMoveOnePositionTo(direction)) {
            newPosition = newPosition.moveOnePosition(direction);
            count++;
            if (newPosition.equals(destination)) {
                return count;
            }
        }
        throw new InvalidPathException();
    }

    public Route createNewRouteOf(final int moveCount) {
        MovingPattern direction = route.getFirst();
        return new Route(Collections.nCopies(moveCount, direction));
    }

    public boolean isReachableByRoute(final JanggiPosition origin, final JanggiPosition destination) {
        JanggiPosition newPosition = origin;

        for (MovingPattern direction : route) {
            if (!newPosition.canMoveOnePositionTo(direction)) {
                return false;
            }
            newPosition = newPosition.moveOnePosition(direction);
        }

        return newPosition.equals(destination);
    }

    public boolean isDirectionContainsIn(final List<MovingPattern> soldierOfChoDirections) {
        MovingPattern direction = route.getFirst();
        return soldierOfChoDirections.contains(direction);
    }

    public boolean isDiagonalDirection() {
        MovingPattern direction = route.getFirst();
        return direction.isDiagonalPattern();
    }

    public List<JanggiPosition> getPositionsOnRouteFrom(final JanggiPosition origin) {
        List<JanggiPosition> positions = new ArrayList<>();
        JanggiPosition position = origin;

        for (MovingPattern direction : route) {
            position = position.moveOnePosition(direction);
            positions.add(position);
        }

        return positions;
    }

    public boolean isEmpty() {
        return route.isEmpty();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(route);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Route route1 = (Route) o;
        return Objects.equals(route, route1.route);
    }
}
