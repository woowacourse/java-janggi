package domain.move.rule;

import domain.intersection.Intersection;
import domain.move.path.Path;
import domain.point.Point;

import java.util.List;

public interface MoveRule {

    List<Point> findPathOfPoints(Intersection origin, Intersection destination);

    void validateMoveRule(Path path);

}
