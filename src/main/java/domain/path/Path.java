package domain.path;

import domain.position.ChessPosition;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public record Path(List<ChessPosition> path) {

    public ChessPosition getDestination() {
        return path.getLast();
    }

    @Override
    public List<ChessPosition> path() {
        return Collections.unmodifiableList(path);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path other = (Path) o;
        return new HashSet<>(path).equals(new HashSet<>(other.path));
    }

    @Override
    public int hashCode() {
        return new HashSet<>(path).hashCode();
    }
}
