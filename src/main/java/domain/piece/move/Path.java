package domain.piece.move;

import domain.intersection.Intersection;

import java.util.List;

public record Path(
        List<Intersection> intersections
) {
}
