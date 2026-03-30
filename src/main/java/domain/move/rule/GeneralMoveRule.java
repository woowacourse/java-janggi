package domain.move.rule;

import domain.intersection.Intersection;
import domain.move.directions.Direction;
import domain.move.directions.Directions;
import domain.move.path.Path;
import domain.point.Point;

import java.util.List;

import static domain.move.directions.Vector.*;
import static domain.move.directions.Vector.LEFT;
import static domain.move.directions.Vector.RIGHT;
import static domain.move.directions.Vector.UP;

public class GeneralMoveRule extends MoveRule {

    public GeneralMoveRule() {
        super(initializeDirections());
    }

    @Override
    public List<Point> findPathOfPoints(Intersection from, Intersection to) {
        return directions.findPoints(from, to);
    }

    @Override
    public boolean checkMoveRule(Path path) {
        path.validateIsSameTeam();
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
