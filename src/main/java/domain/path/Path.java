package domain.path;

import domain.position.ChessPosition;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Path {
    private final List<ChessPosition> path;

    public Path(List<ChessPosition> path) {
        this.path = path;
    }

    public ChessPosition getDestination() {
        return path.getLast();
    }

    public List<ChessPosition> getPath() {
        return Collections.unmodifiableList(path);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final Path path1)) {
            return false;
        }
        return Objects.equals(getPath(), path1.getPath());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPath());
    }
}
