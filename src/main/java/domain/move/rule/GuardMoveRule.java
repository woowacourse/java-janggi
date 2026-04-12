package domain.move.rule;

import static common.constant.JanggiConstant.GENERAL_PIECE_MAX_DISTANCE;
import static domain.move.directions.Vector.*;
import static domain.move.path.exception.PathError.GUARD_CANNOT_GO_OUT_PALACE;

import domain.intersection.Intersection;
import domain.move.directions.Direction;
import domain.move.directions.Directions;
import domain.move.path.Path;
import domain.move.path.exception.PathException;
import domain.point.Point;

import java.util.List;

public class GuardMoveRule implements MoveRule {

    private static final Directions DEFAULT_GUARD_DIRECTIONS = initializeDirections();

    public GuardMoveRule() {
    }

    @Override
    public List<Point> findPathOfPoints(Intersection origin, Intersection destination) {
        Directions availableDirections = getAvailableDirections(origin);
        return availableDirections.findPoints(origin, destination);
    }

    @Override
    public void validateMoveRule(Path path) {
        path.validateIsSameTeam();
        validateDestinationIsPalace(path.getDestination());
    }

    private Directions getAvailableDirections(Intersection origin) {
        if (origin.isPalace()) {
            return DEFAULT_GUARD_DIRECTIONS.add(getPalaceDirections(origin));
        }
        return DEFAULT_GUARD_DIRECTIONS;
    }

    private Directions getPalaceDirections(Intersection origin) {
        return origin.getDiagonalDirections()
                .limitDistance(GENERAL_PIECE_MAX_DISTANCE);
    }

    private void validateDestinationIsPalace(Intersection destination) {
        if (!destination.isPalace()) {
            throw new PathException(GUARD_CANNOT_GO_OUT_PALACE.getMessage());
        }
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
