package domain.board.generator;

import domain.intersection.Intersection;

import java.util.List;

public class DBIntersectionGenerator implements IntersectionGenerator {

    private final List<Intersection> intersections;

    public DBIntersectionGenerator(List<Intersection> intersections) {
        this.intersections = intersections;
    }

    @Override
    public List<Intersection> makeIntersection() {
        return this.intersections;
    }

}
