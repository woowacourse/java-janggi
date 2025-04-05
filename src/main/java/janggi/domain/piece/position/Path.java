package janggi.domain.piece.position;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Path {

    private final List<Position> positions;

    public Path(final List<Position> positions) {
        this.positions = positions;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final Path path)) {
            return false;
        }
        return Objects.equals(positions, path.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(positions);
    }

    public List<Position> getPositions() {
        return Collections.unmodifiableList(positions);
    }
}
