package domain.move.rule;

import domain.intersection.Intersection;
import domain.move.directions.Direction;
import domain.move.directions.Directions;
import domain.move.path.Path;
import domain.point.Point;

import java.util.List;

import static domain.move.directions.Vector.*;

public class HorseMoveRule implements MoveRule {

    private static final Directions DEFAULT_HORSE_DIRECTIONS = initializeDirections();

    public HorseMoveRule() {
    }

    @Override
    public List<Point> findPathOfPoints(Intersection origin, Intersection destination) {
        return DEFAULT_HORSE_DIRECTIONS.findPoints(origin, destination);
    }

    @Override
    public void validateMoveRule(Path path) {
        path.validateIsSameTeam();
        path.validateHasObstacle();
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
