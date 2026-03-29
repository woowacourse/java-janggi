package domain.move.movement;

import domain.board.Intersection;
import java.util.List;

public record Path(Intersection destination, List<Intersection> passingIntersections) {

    public boolean isInBoundsDestination() {
        return destination.isInBounds();
    }
}
