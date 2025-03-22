package janggi;

import janggi.board.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Path {
    private final List<Position> path;

    public Path(List<Position> path) {
        this.path = new ArrayList<>(path);
    }

    public List<Position> getIntermediatePath() {
        path.removeFirst();
        path.removeLast();
        return Collections.unmodifiableList(path);
    }
}
