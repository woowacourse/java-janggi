package domain.piece.move;

import static domain.piece.move.Vector.DOWN;
import static domain.piece.move.Vector.LEFT;
import static domain.piece.move.Vector.RIGHT;
import static domain.piece.move.Vector.UP;

import domain.intersection.Intersection;
import domain.piece.PieceType;
import domain.point.Point;
import java.util.List;

public class ChariotMoveRule extends MoveRule {

    public ChariotMoveRule() {
        super(PieceType.CHARIOT, initializeDirections());
    }

    public static Directions initializeDirections() {
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

    public boolean support(Intersection from) {
        return from.isSamePiece(pieceType);
    }

    public List<Point> findPossiblePoints(Intersection from, Intersection to) {
        return directions.findPoints(from.getPoint(), to.getPoint());
    }

    public boolean checkMoveRule(Intersection from, List<Intersection> path) {
        Intersection to = path.getLast();
        validateIsSameTeam(from, to);
        validateObstacleCondition(path);
        return true;
    }

    private void validateIsSameTeam(Intersection from, Intersection to) {
        if (from.isSameTeam(to)) {
            throw new IllegalArgumentException("같은 팀의 위치로 이동할 수 없습니다.");
        }
    }

    private void validateObstacleCondition(List<Intersection> path) {
        List<Intersection> routeWithoutTarget = path.subList(0, path.size() - 1);

        boolean hasObstacle = routeWithoutTarget.stream()
                .anyMatch(Intersection::hasPiece);

        if (hasObstacle) {
            throw new IllegalArgumentException("이동 경로에 다른 기물이 있어 통과할 수 없습니다.");
        }
    }

}
