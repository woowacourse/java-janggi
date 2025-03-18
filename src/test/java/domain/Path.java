package domain;

import java.util.Collections;
import java.util.List;

public class Path {
    private final List<Position> path;

    public Path(List<Position> path) {
        this.path = path;
    }

    public Position getDestination() {
        return path.getLast();
    }

    public List<Position> getPath() {
        return Collections.unmodifiableList(path);
    }
}
