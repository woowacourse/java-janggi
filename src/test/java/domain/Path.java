package domain;

import java.util.List;

public class Path {
    private final List<Position> path;

    public Path(List<Position> path) {
        this.path = path;
    }

    public Position getDestination() {
        return path.getLast();
    }
}
