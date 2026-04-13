package domain.move;

import domain.board.Intersection;
import java.util.List;

public record Path(Intersection destination, List<Intersection> passingIntersections) {

    public boolean isInBoundsDestination() {
        return destination.isInBounds();
    }

    public static Path of(Intersection destination) {
        return new Path(destination, List.of());
    }
}
