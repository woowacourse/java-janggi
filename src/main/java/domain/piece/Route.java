package domain.piece;

import domain.Point;
import java.util.List;

public final class Route {
    private final List<Direction> directions;

    public Route(List<Direction> directions) {
        this.directions = directions;
    }

    public Point navigateArrivalPoint(Point point) {
        for (Direction direction : directions) {
            point = point.move(direction);
        }
        return point;
    }
}
