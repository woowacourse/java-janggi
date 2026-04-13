package domain.move.rule;

import static domain.move.directions.Vector.DOWN;
import static domain.move.directions.Vector.LEFT;
import static domain.move.directions.Vector.RIGHT;
import static domain.move.directions.Vector.UP;

import domain.intersection.Intersection;
import domain.move.directions.Direction;
import domain.move.directions.Directions;
import domain.move.directions.Vector;
import domain.move.path.Path;
import domain.point.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChariotMoveRule implements MoveRule {

    private static final Directions DEFAULT_CHARIOT_DIRECTIONS = initializeDirections();

    public ChariotMoveRule() {
    }

    @Override
    public List<Point> findPathOfPoints(Intersection origin, Intersection destination) {
        if (origin.isPalace()) {
            return DEFAULT_CHARIOT_DIRECTIONS.add(origin.getDiagonalDirections())
                    .findPoints(origin, destination);
        }

        return DEFAULT_CHARIOT_DIRECTIONS.findPoints(origin, destination);
    }

    @Override
    public void validateMoveRule(Path path) {
        path.validateIsSameTeam();
        path.validateHasObstacle();
    }

    private static Directions initializeDirections() {
        List<Direction> allDirections = new ArrayList<>();
        allDirections.addAll(generateLinearDirections(UP, 9));
        allDirections.addAll(generateLinearDirections(DOWN, 9));
        allDirections.addAll(generateLinearDirections(RIGHT, 8));
        allDirections.addAll(generateLinearDirections(LEFT, 8));
        return new Directions(allDirections);
    }

    private static List<Direction> generateLinearDirections(Vector vector, int maxDistance) {
        List<Direction> directions = new ArrayList<>();
        for (int i = 1; i <= maxDistance; i++) {
            directions.add(new Direction(Collections.nCopies(i, vector)));
        }
        return directions;
    }

}
