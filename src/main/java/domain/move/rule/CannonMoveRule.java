package domain.move.rule;

import static domain.move.directions.Vector.*;
import static domain.move.directions.Vector.LEFT;
import static domain.move.directions.Vector.RIGHT;
import static domain.move.path.exception.PathError.*;

import domain.intersection.Intersection;
import domain.move.directions.Direction;
import domain.move.directions.Directions;
import domain.move.directions.Vector;
import domain.move.path.Path;
import domain.move.path.exception.PathException;
import domain.point.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CannonMoveRule implements MoveRule {

    private static final Directions DEFAULT_CANNON_DIRECTIONS = initializeDirections();
    private static final int CANNON_JUMP_OBSTACLE_COUNT = 1;

    public CannonMoveRule() {
    }

    @Override
    public List<Point> findPathOfPoints(Intersection origin, Intersection destination) {
        if (origin.isPalace()) {
            return DEFAULT_CANNON_DIRECTIONS.add(origin.getDiagonalDirections())
                    .findPoints(origin, destination);
        }

        return DEFAULT_CANNON_DIRECTIONS.findPoints(origin, destination);
    }

    @Override
    public void validateMoveRule(Path path) {
        path.validateIsSameTeam();
        validateCannonObstacleCondition(path);
        validateDestinationIsNotCannon(path);
    }

    private void validateCannonObstacleCondition(Path path) {
        List<Intersection> obstacles = path.getObstacleIntersection();
        validateObstacleIsOnly(obstacles);
        validateObstacleIsNotCannon(obstacles.getFirst());
    }

    private void validateObstacleIsOnly(List<Intersection> obstacles) {
        if (obstacles.size() != CANNON_JUMP_OBSTACLE_COUNT) {
            throw new PathException(CANNON_MUST_JUMP_ONE_PIECE.getMessage());
        }
    }

    private void validateObstacleIsNotCannon(Intersection obstacle) {
        if (obstacle.hasCannon()) {
            throw new PathException(CANNON_CANNOT_JUMP_CANNON.getMessage());
        }
    }

    private void validateDestinationIsNotCannon(Path path) {
        if (path.getDestination().hasCannon()) {
            throw new PathException(CANNON_CANNOT_ATTACK_CANNON.getMessage());
        }
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
