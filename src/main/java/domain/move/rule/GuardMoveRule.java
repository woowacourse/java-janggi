package domain.move.rule;

import static domain.move.directions.Vector.*;

import domain.intersection.Intersection;
import domain.move.directions.Direction;
import domain.move.directions.Directions;
import domain.move.path.Path;
import domain.point.Point;
import java.util.List;

public class GuardMoveRule implements MoveRule {

    private static final Directions DEFAULT_GUARD_DIRECTIONS = initializeDirections();

    public GuardMoveRule() {
    }

    @Override
    public List<Point> findPathOfPoints(Intersection from, Intersection to) {
        return DEFAULT_GUARD_DIRECTIONS.findPoints(from, to);
    }

    @Override
    public boolean checkMoveRule(Path path) {
        path.validateIsSameTeam();
        path.validateHasObstacle();
        return true;
    }

    // NOTE 사이클 1에서는 궁성이 없으므로, 상하좌우만 설정
    public static Directions initializeDirections() {
        return new Directions(List.of(
                new Direction(List.of(UP)),
                new Direction(List.of(DOWN)),
                new Direction(List.of(RIGHT)),
                new Direction(List.of(LEFT)))
        );
    }

}
