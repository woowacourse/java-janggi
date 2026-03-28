package domain.move.path;

import domain.intersection.Intersection;
import domain.piece.PieceType;

import java.util.List;

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
            throw new IllegalArgumentException("같은 팀의 위치로 이동할 수 없습니다.");
        }
    }

    public void validateHasObstacle() {
        if (hasObstacle()) {
            throw new IllegalArgumentException("이동 경로에 다른 기물이 있어 통과할 수 없습니다.");
        }
    }

    public void validateCannonObstacleCondition() {
        validateObstacleIsOnly();
        validateObstacleIsNotCannon(getObstacleIntersection().getFirst());
    }

    private void validateObstacleIsOnly() {
        if (getObstacleIntersection().size() != CANNON_JUMP_OBSTACLE_CONDITION) {
            throw new IllegalArgumentException("포는 반드시 기물 하나를 넘어야 합니다.");
        }
    }

    private static void validateObstacleIsNotCannon(Intersection obstacle) {
        if (obstacle.isSamePiece(PieceType.CANNON)) {
            throw new IllegalArgumentException("포는 포를 넘어갈 수 없습니다.");
        }
    }

    public void validateDestinationIsNotCannon() {
        if (getLastIntersection().isSamePiece(PieceType.CANNON)) {
            throw new IllegalArgumentException("포는 포를 공격할 수 없습니다.");
        }
    }

}
