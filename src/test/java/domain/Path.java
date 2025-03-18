package domain;

import java.util.Collections;
import java.util.List;

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
}
