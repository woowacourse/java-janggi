package janggi.position;

import java.util.List;
import java.util.Objects;

public class Path {

    private final List<Position> positions;

    public Path(List<Position> positions) {
        this.positions = positions;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Path path)) {
            return false;
        }
        return Objects.equals(positions, path.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(positions);
    }

    public List<Position> getPositions() {
        return positions;
    }
}
