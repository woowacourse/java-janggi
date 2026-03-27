package domain.fixture;

import domain.board.IntersectionGenerator;
import domain.intersection.Intersection;
import java.util.List;

public class TestIntersectionGenerator implements IntersectionGenerator {
    List<Intersection> intersections;

    public TestIntersectionGenerator(List<Intersection> intersections) {
        this.intersections = intersections;
    }

    public List<Intersection> makeIntersection() {
        return this.intersections;
    }
}
