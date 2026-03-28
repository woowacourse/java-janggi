package domain.piece.move;

import domain.intersection.Intersection;
import domain.piece.PieceType;
import domain.point.Point;

import java.util.List;
import java.util.stream.IntStream;

import static domain.piece.move.Vector.*;

public class HorseMoveRule extends MoveRule {

    public HorseMoveRule() {
        super(PieceType.HORSE, initializeDirections());
    }

    public boolean support(Intersection from) {
        return from.isSamePiece(pieceType);
    }

    public List<Point> findPathOfPoints(Intersection from, Intersection to) {
        return directions.findPoints(from.getPoint(), to.getPoint());
    }

    public boolean checkMoveRule(Intersection from, List<Intersection> path) {
        Intersection to = path.getLast();
        validateIsSameTeam(from, to); // 도착지가 같은 팀인지 확인
        validateObstacleCondition(path); // 중간에 장애물이 있는 지 확인
        return true;
    }

    // 같은 팀 인지 확인
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

}
