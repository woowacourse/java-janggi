package domain.move.rule;

import domain.intersection.Intersection;
import domain.move.directions.Direction;
import domain.move.directions.Directions;
import domain.move.path.Path;
import domain.piece.PieceType;
import domain.point.Point;

import java.util.List;

import static domain.move.directions.Vector.*;
import static domain.move.directions.Vector.LEFT;
import static domain.move.directions.Vector.RIGHT;

public class CannonMoveRule extends MoveRule {

    private static final int JUMP_OBSTACLE_CONDITION = 1;

    public CannonMoveRule() {
        super(PieceType.CANNON, initializeDirections());
    }

    @Override
    public boolean support(Intersection from) {
        return from.isSamePiece(pieceType);
    }

    @Override
    public List<Point> findPathOfPoints(Intersection from, Intersection to) {
        return directions.findPoints(from, to);
    }

    @Override
    public boolean checkMoveRule(Intersection from, Path path) {
        Intersection to = path.getLastIntersection();
        validateIsSameTeam(from, to);
        validateObstacleCondition(from, path);
        validateDestinationIsNotCannon(from, to);
        return true;
    }

    private void validateIsSameTeam(Intersection from, Intersection to) {
        if (from.isSameTeam(to)) {
            throw new IllegalArgumentException("같은 팀의 위치로 이동할 수 없습니다.");
        }
    }

    private void validateObstacleCondition(Intersection from, Path path) {
        List<Intersection> obstacleIntersections = path.getObstacleIntersection();
        validateObstacleIsOnly(obstacleIntersections);
        validateObstacleIsNotCannon(from, obstacleIntersections);
    }

    private void validateObstacleIsOnly(List<Intersection> list) {
        if (list.size() != JUMP_OBSTACLE_CONDITION) {
            throw new IllegalArgumentException("포는 반드시 기물 하나를 넘어야 합니다.");
        }
    }

    private static void validateObstacleIsNotCannon(Intersection from, List<Intersection> list) {
        if (list.getFirst().isSamePiece(from)) {
            throw new IllegalArgumentException("포는 포를 넘어갈 수 없습니다.");
        }
    }

    private void validateDestinationIsNotCannon(Intersection from, Intersection to) {
        if (from.isSamePiece(to)) {
            throw new IllegalArgumentException("포는 포를 공격할 수 없습니다.");
        }
    }

    public static Directions initializeDirections() {
        return new Directions(List.of(
                new Direction(List.of(UP)),
                new Direction(List.of(UP, UP)),
                new Direction(List.of(UP, UP, UP)),
                new Direction(List.of(UP, UP, UP, UP)),
                new Direction(List.of(UP, UP, UP, UP, UP)),
                new Direction(List.of(UP, UP, UP, UP, UP, UP)),
                new Direction(List.of(UP, UP, UP, UP, UP, UP, UP)),
                new Direction(List.of(UP, UP, UP, UP, UP, UP, UP, UP)),
                new Direction(List.of(UP, UP, UP, UP, UP, UP, UP, UP, UP)),

                new Direction(List.of(DOWN)),
                new Direction(List.of(DOWN, DOWN)),
                new Direction(List.of(DOWN, DOWN, DOWN)),
                new Direction(List.of(DOWN, DOWN, DOWN, DOWN)),
                new Direction(List.of(DOWN, DOWN, DOWN, DOWN, DOWN)),
                new Direction(List.of(DOWN, DOWN, DOWN, DOWN, DOWN, DOWN)),
                new Direction(List.of(DOWN, DOWN, DOWN, DOWN, DOWN, DOWN, DOWN)),
                new Direction(List.of(DOWN, DOWN, DOWN, DOWN, DOWN, DOWN, DOWN, DOWN)),
                new Direction(List.of(DOWN, DOWN, DOWN, DOWN, DOWN, DOWN, DOWN, DOWN, DOWN)),

                new Direction(List.of(RIGHT)),
                new Direction(List.of(RIGHT, RIGHT)),
                new Direction(List.of(RIGHT, RIGHT, RIGHT)),
                new Direction(List.of(RIGHT, RIGHT, RIGHT, RIGHT)),
                new Direction(List.of(RIGHT, RIGHT, RIGHT, RIGHT, RIGHT)),
                new Direction(List.of(RIGHT, RIGHT, RIGHT, RIGHT, RIGHT, RIGHT)),
                new Direction(List.of(RIGHT, RIGHT, RIGHT, RIGHT, RIGHT, RIGHT, RIGHT)),
                new Direction(List.of(RIGHT, RIGHT, RIGHT, RIGHT, RIGHT, RIGHT, RIGHT, RIGHT)),

                new Direction(List.of(LEFT)),
                new Direction(List.of(LEFT, LEFT)),
                new Direction(List.of(LEFT, LEFT, LEFT)),
                new Direction(List.of(LEFT, LEFT, LEFT, LEFT)),
                new Direction(List.of(LEFT, LEFT, LEFT, LEFT, LEFT)),
                new Direction(List.of(LEFT, LEFT, LEFT, LEFT, LEFT, LEFT)),
                new Direction(List.of(LEFT, LEFT, LEFT, LEFT, LEFT, LEFT, LEFT)),
                new Direction(List.of(LEFT, LEFT, LEFT, LEFT, LEFT, LEFT, LEFT, LEFT))
        ));
    }

}
