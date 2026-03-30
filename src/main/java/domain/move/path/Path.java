package domain.move.path;

import domain.intersection.Intersection;
import domain.move.path.exception.PathException;
import domain.piece.PieceType;

import java.util.List;

import static domain.move.path.exception.PathError.*;

public record Path(
        List<Intersection> intersections
) {

    private static final int EXCEPT_DESTINATION = 1;
    private static final int CANNON_JUMP_OBSTACLE_CONDITION = 1;

    public List<Intersection> getPathWithoutLast() {
        return intersections.subList(0, intersections.size() - EXCEPT_DESTINATION);
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

    public void validateIsSameTeam(Intersection from) {
        if (from.isSameTeam(getLastIntersection())) {
            throw new PathException(CANNOT_MOVE_DESTINATION_IS_SAME_TEAM.getMessage());
        }
    }

    public void validateHasObstacle() {
        if (hasObstacle()) {
            throw new PathException(CANNOT_MOVE_PATH_HAS_OBSTACLE.getMessage());
        }
    }

    public void validateCannonObstacleCondition() {
        validateObstacleIsOnly();
        validateObstacleIsNotCannon(getObstacleIntersection().getFirst());
    }

    private void validateObstacleIsOnly() {
        if (getObstacleIntersection().size() != CANNON_JUMP_OBSTACLE_CONDITION) {
            throw new PathException(CANNON_MUST_JUMP_ONE_PIECE.getMessage());
        }
    }

    private static void validateObstacleIsNotCannon(Intersection obstacle) {
        if (obstacle.isSamePiece(PieceType.CANNON)) {
            throw new PathException(CANNON_CANNOT_JUMP_CANNON.getMessage());
        }
    }

    public void validateDestinationIsNotCannon() {
        if (getLastIntersection().isSamePiece(PieceType.CANNON)) {
            throw new PathException(CANNON_CANNOT_ATTACK_CANNON.getMessage());
        }
    }

}
