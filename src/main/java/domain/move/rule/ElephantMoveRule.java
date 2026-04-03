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
import domain.move.path.Path;
import domain.point.Point;

import java.util.List;

public class ElephantMoveRule implements MoveRule {

    private static final Directions DEFAULT_ELEPHANT_DIRECTIONS = initializeDirections();

    public ElephantMoveRule() {
    }

    @Override
    public List<Point> findPathOfPoints(Intersection origin, Intersection destination) {
        return DEFAULT_ELEPHANT_DIRECTIONS.findPoints(origin, destination);
    }

    @Override
    public void validateMoveRule(Path path) {
        path.validateIsSameTeam();
        path.validateHasObstacle();
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
