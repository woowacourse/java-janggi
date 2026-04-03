package domain.piece.move;

import static domain.piece.move.Vector.DOWN;
import static domain.piece.move.Vector.LEFT;
import static domain.piece.move.Vector.LEFT_DOWN;
import static domain.piece.move.Vector.LEFT_UP;
import static domain.piece.move.Vector.RIGHT;
import static domain.piece.move.Vector.RIGHT_DOWN;
import static domain.piece.move.Vector.RIGHT_UP;
import static domain.piece.move.Vector.UP;

import domain.intersection.Intersection;
import domain.piece.PieceType;
import domain.point.Point;
import java.util.List;

public class HorseMoveRule extends MoveRule {

    public HorseMoveRule() {
        super(PieceType.HORSE, initializeDirections());
    }

    public static Directions initializeDirections() {
        return new Directions(List.of(
                new Direction(List.of(UP, LEFT_UP)),
                new Direction(List.of(UP, RIGHT_UP)),
                new Direction(List.of(DOWN, LEFT_DOWN)),
                new Direction(List.of(DOWN, RIGHT_DOWN)),
                new Direction(List.of(LEFT, LEFT_UP)),
                new Direction(List.of(LEFT, LEFT_DOWN)),
                new Direction(List.of(RIGHT, RIGHT_UP)),
                new Direction(List.of(RIGHT, RIGHT_DOWN))
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

    private void validateObstacleCondition(List<Intersection> path) {
        List<Intersection> routeWithoutTarget = path.subList(0, path.size() - 1);

        boolean hasObstacle = routeWithoutTarget.stream()
                .anyMatch(Intersection::hasPiece);

        if (hasObstacle) {
            throw new IllegalArgumentException("이동 경로에 다른 기물이 있어 통과할 수 없습니다.");
        }
    }

}
