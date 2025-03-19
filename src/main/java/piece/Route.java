package piece;

import java.util.Collections;
import java.util.List;

public class Route {

    private final List<Position> positions;

    public Route(final List<Position> positions) {
        this.positions = positions;
    }

    public List<Position> getPositions() {
        return Collections.unmodifiableList(positions);
    }
}
