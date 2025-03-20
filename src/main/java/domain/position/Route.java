package domain.position;

import java.util.Collections;
import java.util.List;

public class Route {
    private final List<Position> positions;

    private Route(List<Position> positions) {
        this.positions = positions;
    }

    public static Route of(List<Position> positions) {
        return new Route(positions);
    }

    public Position searchEndPoint() {
        return positions.getLast();
    }

    public List<Position> getPoints() {
        return Collections.unmodifiableList(positions);
    }

    public List<Position> getPointsExceptEndPoint() {
        return positions.subList(0, positions.size() - 1);
    }
}
