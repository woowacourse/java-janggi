package domain.fixture;

import domain.board.IntersectionGenerator;
import domain.intersection.Intersection;
import java.util.List;

public class TestIntersectionGenerator implements IntersectionGenerator {
    private final List<Intersection> intersections;

    public TestIntersectionGenerator(List<Intersection> intersections) {
        this.intersections = intersections;
    }

    @Override
    public List<Intersection> makePieceIntersections() {
        return intersections;
    }
}
