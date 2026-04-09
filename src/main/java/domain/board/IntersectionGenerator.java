package domain.board;

import domain.intersection.Intersection;
import java.util.List;

public interface IntersectionGenerator {
    List<Intersection> makePieceIntersections();
}
