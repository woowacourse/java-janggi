package domain.path;

import domain.position.JanggiPosition;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Path {
    private final List<JanggiPosition> path;

    public Path(List<JanggiPosition> path) {
        this.path = path;
    }

    public JanggiPosition getDestination() {
        return path.getLast();
    }

    public List<JanggiPosition> getPath() {
        return Collections.unmodifiableList(path);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Path that = (Path) object;
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
