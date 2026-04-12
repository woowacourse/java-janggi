package domain.move.rule;

import static common.constant.JanggiConstant.GENERAL_PIECE_MAX_DISTANCE;
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
        Directions availableDirections = getAvailableDirections(origin);
        return availableDirections.findPoints(origin, destination);
    }

    @Override
    public void validateMoveRule(Path path) {
        path.validateIsSameTeam();
    }

    private Directions getAvailableDirections(Intersection origin) {
        Directions forwardDirections = DEFAULT_SOLIDER_DIRECTIONS.toForward(origin);
        if (origin.isPalace()) {
            Directions forwardDiagonal = getForwardDiagonal(origin);
            return forwardDirections.add(forwardDiagonal);
        }

        return forwardDirections;
    }

    private Directions getForwardDiagonal(Intersection origin) {
        return origin.getDiagonalDirections()
                .limitDistance(GENERAL_PIECE_MAX_DISTANCE)
                .toForward(origin);
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
