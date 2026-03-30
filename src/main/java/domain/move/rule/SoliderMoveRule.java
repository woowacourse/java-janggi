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

public class SoliderMoveRule extends MoveRule {

    public SoliderMoveRule() {
        super(initializeDirections());
    }

    @Override
    public List<Point> findPathOfPoints(Intersection from, Intersection to) {
        Directions directions = getDirections(from);
        return directions.findPoints(from, to);
    }

    @Override
    public boolean checkMoveRule(Path path) {
        path.validateIsSameTeam();
        return true;
    }

    public static Directions initializeDirections() {
        return new Directions(List.of(
                new Direction(List.of(DOWN)),
                new Direction(List.of(RIGHT)),
                new Direction(List.of(LEFT)))
        );
    }

    public Directions getDirections(Intersection from){
        if (from.isChoIntersection()) {
            return new Directions(List.of(
                    new Direction(List.of(UP)),
                    new Direction(List.of(RIGHT)),
                    new Direction(List.of(LEFT))));
        }
        return directions;
    }

}
