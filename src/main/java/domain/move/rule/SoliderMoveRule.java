package domain.move.rule;

import static domain.move.directions.Vector.*;
import static domain.move.directions.Vector.LEFT;
import static domain.move.directions.Vector.RIGHT;

import domain.intersection.Intersection;
import domain.move.directions.Direction;
import domain.move.directions.Directions;
import domain.move.path.Path;
import domain.point.Point;

import java.util.List;

public class SoliderMoveRule implements MoveRule {

    private static final Directions DEFAULT_SOLIDER_DIRECTIONS = initializeDirections();


    public SoliderMoveRule() {
    }

    @Override
    public List<Point> findPathOfPoints(Intersection origin, Intersection destination) {
        Directions forward = DEFAULT_SOLIDER_DIRECTIONS.toForward(origin);
        return forward.findPoints(origin, destination);
    }

    @Override
    public void validateMoveRule(Path path) {
        path.validateIsSameTeam();
    }

    public static Directions initializeDirections() {
        return new Directions(List.of(
                new Direction(List.of(UP)),
                new Direction(List.of(DOWN)),
                new Direction(List.of(RIGHT)),
                new Direction(List.of(LEFT)))
        );
    }

}
