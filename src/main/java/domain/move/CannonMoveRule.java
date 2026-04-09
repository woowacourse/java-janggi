package domain.move;

import static domain.move.Vector.DOWN;
import static domain.move.Vector.LEFT;
import static domain.move.Vector.LEFT_DOWN;
import static domain.move.Vector.LEFT_UP;
import static domain.move.Vector.RIGHT;
import static domain.move.Vector.RIGHT_DOWN;
import static domain.move.Vector.RIGHT_UP;
import static domain.move.Vector.UP;

import domain.intersection.Intersection;
import domain.piece.PieceType;
import domain.point.Point;
import java.util.List;

public class CannonMoveRule extends MoveRule {
    public CannonMoveRule() {
        super(PieceType.CANNON);
    }

    private static void validateObstacleIsNotCannon(Intersection from, List<Intersection> list) {
        if (list.getFirst().isSamePiece(from)) {
            throw new IllegalArgumentException("포는 포를 넘어갈 수 없습니다.");
        }
    }

    private Directions defaultDirection() {
        return new Directions(List.of(
                Direction.straight(UP, 1),
                Direction.straight(UP, 2),
                Direction.straight(UP, 3),
                Direction.straight(UP, 4),
                Direction.straight(UP, 5),
                Direction.straight(UP, 6),
                Direction.straight(UP, 7),
                Direction.straight(UP, 8),
                Direction.straight(UP, 9),

                Direction.straight(DOWN, 1),
                Direction.straight(DOWN, 2),
                Direction.straight(DOWN, 3),
                Direction.straight(DOWN, 4),
                Direction.straight(DOWN, 5),
                Direction.straight(DOWN, 6),
                Direction.straight(DOWN, 7),
                Direction.straight(DOWN, 8),
                Direction.straight(DOWN, 9),

                Direction.straight(LEFT, 1),
                Direction.straight(LEFT, 2),
                Direction.straight(LEFT, 3),
                Direction.straight(LEFT, 4),
                Direction.straight(LEFT, 5),
                Direction.straight(LEFT, 6),
                Direction.straight(LEFT, 7),
                Direction.straight(LEFT, 8),
                Direction.straight(LEFT, 9),

                Direction.straight(RIGHT, 1),
                Direction.straight(RIGHT, 2),
                Direction.straight(RIGHT, 3),
                Direction.straight(RIGHT, 4),
                Direction.straight(RIGHT, 5),
                Direction.straight(RIGHT, 6),
                Direction.straight(RIGHT, 7),
                Direction.straight(RIGHT, 8),
                Direction.straight(RIGHT, 9)
        ));
    }

    private Directions palaceDirection() {
        return new Directions(List.of(
                Direction.straight(LEFT_UP, 1),
                Direction.straight(LEFT_UP, 2),

                Direction.straight(LEFT_DOWN, 1),
                Direction.straight(LEFT_DOWN, 2),

                Direction.straight(RIGHT_UP, 1),
                Direction.straight(RIGHT_UP, 2),

                Direction.straight(RIGHT_DOWN, 1),
                Direction.straight(RIGHT_DOWN, 2)
        ));
    }

    public boolean support(Intersection from) {
        return from.isSamePiece(pieceType);
    }

    public List<Point> findPossiblePoints(Intersection from, Intersection to) {
        Directions directions = makeDirections(from, to);
        return directions.findPoints(from.getPoint(), to.getPoint());
    }

    protected Directions makeDirections(Intersection from, Intersection to) {
        if (from.isPalaceDiagonal() && to.isPalaceDiagonal()) {
            return defaultDirection().merge(palaceDirection());
        }
        return defaultDirection();
    }

    public void validateMoveRule(Intersection from, List<Intersection> path) {
        Intersection to = path.getLast();
        validateIsSameTeam(from, to);
        validateObstacleCondition(from, path);
        validateDestinationIsNotCannon(from, to);
    }

    private void validateObstacleCondition(Intersection from, List<Intersection> path) {
        List<Intersection> obstacles = path.subList(0, path.size() - 1).stream()
                .filter(Intersection::hasPiece)
                .toList();

        validateObstacleIsOnly(obstacles);
        validateObstacleIsNotCannon(from, obstacles);
    }

    private void validateObstacleIsOnly(List<Intersection> list) {
        if (list.size() != 1) {
            throw new IllegalArgumentException("포는 반드시 기물 하나를 넘어야 합니다.");
        }
    }

    private void validateDestinationIsNotCannon(Intersection from, Intersection to) {
        if (from.isSamePiece(to)) {
            throw new IllegalArgumentException("포는 포를 공격할 수 없습니다.");
        }
    }
}
