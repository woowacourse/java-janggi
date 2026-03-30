package domain.move.rule;

import domain.intersection.Intersection;
import domain.move.directions.Directions;
import domain.move.path.Path;
import domain.point.Point;
import java.util.List;

public abstract class MoveRule {

    protected final Directions directions;

    protected MoveRule(Directions directions) {
        this.directions = directions;
    }

    public abstract List<Point> findPathOfPoints(Intersection from, Intersection to);

    public abstract boolean checkMoveRule(Path path);

}
