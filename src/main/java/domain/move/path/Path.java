package domain.move.path;

import domain.intersection.Intersection;
import domain.move.path.exception.PathException;

import java.util.ArrayList;
import java.util.List;

import static domain.move.path.exception.PathError.*;

public record Path(
        List<Intersection> intersections
) {

    private static final int EXCEPT_ORIGIN = 1;
    private static final int EXCEPT_DESTINATION = 1;

    public Path addOrigin(Intersection origin) {
        List<Intersection> addOriginPath = new ArrayList<>(intersections);
        addOriginPath.addFirst(origin);
        return new Path(addOriginPath);
    }

    public Intersection getOrigin() {
        return intersections.getFirst();
    }

    public Intersection getDestination() {
        return intersections.getLast();
    }

    public boolean hasObstacle() {
        return getPathWithoutOriginAndDestination().stream()
                .anyMatch(Intersection::hasPiece);
    }

    public List<Intersection> getObstacleIntersection() {
        return getPathWithoutOriginAndDestination().stream()
                .filter(Intersection::hasPiece)
                .toList();
    }

    public List<Intersection> getPathWithoutOriginAndDestination() {
        if (intersections.size() <= EXCEPT_ORIGIN + EXCEPT_DESTINATION) {
            return List.of();
        }
        return intersections.subList(EXCEPT_ORIGIN, intersections.size() - EXCEPT_DESTINATION);
    }

    public void validateIsSameTeam() {
        if (getOrigin().isSameTeam(getDestination())) {
            throw new PathException(CANNOT_MOVE_DESTINATION_IS_SAME_TEAM.getMessage());
        }
    }

    public void validateHasObstacle() {
        if (hasObstacle()) {
            throw new PathException(CANNOT_MOVE_PATH_HAS_OBSTACLE.getMessage());
        }
    }

}
