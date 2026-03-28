package domain.move.path;

import domain.intersection.Intersection;

import java.util.List;

public record Path(
        List<Intersection> intersections
) {
}
