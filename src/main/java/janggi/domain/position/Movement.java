package janggi.domain.position;

import janggi.exception.ErrorException;
import java.util.HashSet;
import java.util.Set;

public record Movement(Position origin, Position target) {

    public Movement {
        if (origin.equals(target)) {
            throw new ErrorException("출발 위치와 도착 위치가 같은 경우 움직일 수 없습니다.");
        }
    }

    public boolean isHorizontal() {
        return origin.isHorizontalTo(target);
    }

    public boolean isVertical() {
        return origin.isVerticalTo(target);
    }

    public int calculateXDistance() {
        return origin.calculateXDistanceTo(target);
    }

    public int calculateYDistance() {
        return origin.calculateYDistance(target);
    }

    public Set<Position> findRoute() {
        if (isHorizontal()) {
            return findHorizontalRoute();
        }
        return findVerticalRoute();
    }

    private Set<Position> findHorizontalRoute() {
        Set<Position> route = new HashSet<>();
        int start = Math.min(origin.x(), target.x());
        int end = Math.max(origin.x(), target.x());
        for (int i = start + 1; i < end; i++) {
            route.add(Position.of(i, origin.y()));
        }
        return route;
    }

    private Set<Position> findVerticalRoute() {
        Set<Position> route = new HashSet<>();
        int start = Math.min(origin.y(), target.y());
        int end = Math.max(origin.y(), target.y());
        for (int i = start + 1; i < end; i++) {
            route.add(Position.of(origin.x(), i));
        }
        return route;
    }
}
