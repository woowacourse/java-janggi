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
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Path path1 = (Path) object;
        return Objects.equals(path, path1.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
