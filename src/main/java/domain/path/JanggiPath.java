package domain.path;

import domain.position.JanggiPosition;

import java.util.Collections;
import java.util.List;

public class JanggiPath {
    private final List<JanggiPosition> path;

    public JanggiPath(List<JanggiPosition> path) {
        this.path = path;
    }

    public JanggiPosition getDestination() {
        return path.getLast();
    }

    public List<JanggiPosition> getPath() {
        return Collections.unmodifiableList(path);
    }
}
