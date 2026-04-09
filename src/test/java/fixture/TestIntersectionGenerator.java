package fixture;

import domain.board.generator.IntersectionGenerator;
import domain.intersection.Intersection;

import java.util.List;

public class TestIntersectionGenerator implements IntersectionGenerator {

    private final List<Intersection> intersections;

    public TestIntersectionGenerator(List<Intersection> intersections) {
        this.intersections = intersections;
    }

    public List<Intersection> makeIntersection() {
        return this.intersections;
    }

}
