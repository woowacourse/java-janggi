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

    private static final Directions DEFAULT_HAN_DIRECTIONS = new Directions(List.of(
            new Direction(List.of(DOWN)),
            new Direction(List.of(RIGHT)),
            new Direction(List.of(LEFT)))
    );

    private static final Directions DEFAULT_CHO_DIRECTIONS = new Directions(List.of(
            new Direction(List.of(DOWN)),
            new Direction(List.of(RIGHT)),
            new Direction(List.of(LEFT)))
    );

    public SoliderMoveRule() {
    }

    @Override
    public List<Point> findPathOfPoints(Intersection origin, Intersection destination) {
        Directions directions = getDirections(origin);
        return directions.findPoints(origin, destination);
    }

    @Override
    public boolean checkMoveRule(Path path) {
        path.validateIsSameTeam();
        return true;
    }


    public Directions getDirections(Intersection origin){
        if (origin.isChoIntersection()) {
            return DEFAULT_CHO_DIRECTIONS;
        }
        return DEFAULT_HAN_DIRECTIONS;
    }

}
