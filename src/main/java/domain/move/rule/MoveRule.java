package domain.move.rule;

import domain.intersection.Intersection;
import domain.move.directions.Directions;
import domain.move.path.Path;
import domain.point.Point;
import java.util.List;

public interface MoveRule {

    List<Point> findPathOfPoints(Intersection from, Intersection to);

    boolean checkMoveRule(Path path);

//    Directions getDirections();

}
