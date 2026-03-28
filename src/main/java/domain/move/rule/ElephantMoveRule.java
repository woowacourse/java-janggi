package domain.move.rule;

import static domain.move.directions.Vector.DOWN;
import static domain.move.directions.Vector.LEFT;
import static domain.move.directions.Vector.LEFT_DOWN;
import static domain.move.directions.Vector.LEFT_UP;
import static domain.move.directions.Vector.RIGHT;
import static domain.move.directions.Vector.RIGHT_DOWN;
import static domain.move.directions.Vector.RIGHT_UP;
import static domain.move.directions.Vector.UP;

import domain.intersection.Intersection;
import domain.move.directions.Direction;
import domain.move.directions.Directions;
import domain.piece.PieceType;
import domain.point.Point;
import java.util.List;

public class ElephantMoveRule extends MoveRule {

    public ElephantMoveRule() {
        super(PieceType.ELEPHANT, initializeDirections());
    }

    @Override
    public boolean support(Intersection from) {
        return from.isSamePiece(pieceType);
    }

    @Override
    public List<Point> findPathOfPoints(Intersection from, Intersection to) {
        return directions.findPoints(from.getPoint(), to.getPoint());
    }

    @Override
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

    public static Directions initializeDirections() {
        return new Directions(List.of(
                new Direction(List.of(UP, LEFT_UP, LEFT_UP)),
                new Direction(List.of(UP, RIGHT_UP, RIGHT_UP)),
                new Direction(List.of(DOWN, LEFT_DOWN, LEFT_DOWN)),
                new Direction(List.of(DOWN, RIGHT_DOWN, RIGHT_DOWN)),
                new Direction(List.of(LEFT, LEFT_UP, LEFT_UP)),
                new Direction(List.of(LEFT, LEFT_DOWN, LEFT_DOWN)),
                new Direction(List.of(RIGHT, RIGHT_UP, RIGHT_UP)),
                new Direction(List.of(RIGHT, RIGHT_DOWN, RIGHT_DOWN))
        ));
    }

}
