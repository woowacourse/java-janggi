package janggi.domain.piece;

import janggi.domain.Position;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Route {
    private final List<Position> route;

    public Route(List<Position> route) {
        this.route = route;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Route other = (Route) o;
        return Objects.equals(route, other.route);
    }

    @Override
    public int hashCode() {
        return Objects.hash(route);
    }

    public boolean isEveryBetween(Predicate<Position> predicate) {
        if (route.size() <= 2) {
            return true;
        }
        return getBetween().stream()
                .allMatch(predicate);
    }

    public boolean isAnyBetween(Predicate<Position> predicate) {
        if (route.size() <= 2) {
            return false;
        }
        return getBetween().stream()
                .anyMatch(predicate);
    }

    public int countBetween(Predicate<Position> condition) {
        if (route.size() <= 2) {
            return 0;
        }
        return (int) getBetween().stream()
                .filter(condition)
                .count();
    }

    public boolean isArrivalPoint(Position position) {
        return route.getLast().equals(position);
    }

    public boolean isDestinationSatisfied(Predicate<Position> condition) {
        Position destination = route.getLast();
        return condition.test(destination);
    }

    private List<Position> getBetween() {
        return route.subList(1, route.size() - 1);
    }
}
