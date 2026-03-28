package domain.move.path;

import domain.intersection.Intersection;

import java.util.List;

public record Path(
        List<Intersection> intersections
) {

    private static final int EXCEPT_LAST_INTERSECTION = 1;

    public List<Intersection> getPathWithoutLast() {
        return intersections.subList(0, intersections.size() - EXCEPT_LAST_INTERSECTION);
    }

    public Intersection getLastIntersection() {
        return intersections.getLast();
    }

    public boolean hasObstacle() {
        return getPathWithoutLast().stream()
                .anyMatch(Intersection::hasPiece);
    }

    public List<Intersection> getObstacleIntersection() {
        return getPathWithoutLast().stream()
                .filter(Intersection::hasPiece)
                .toList();
    }

}
