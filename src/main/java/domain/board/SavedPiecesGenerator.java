package domain.board;

import domain.intersection.Intersection;
import java.util.List;

public class SavedPiecesGenerator implements IntersectionGenerator {
    private final List<Intersection> intersections;

    public SavedPiecesGenerator(List<Intersection> intersections) {
        this.intersections = intersections;
    }

    @Override
    public List<Intersection> makePieceIntersections() {
        return intersections;
    }
}
