package domain.coordinate;

import java.util.List;

public final class Path {

    private final List<Position> positions;

    public Path(List<Position> positions) {
        this.positions = positions;
    }

    public List<Position> getPositions() {
        return this.positions;
    }
}
