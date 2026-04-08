package domain.path;

import domain.movement.Direction;
import java.util.List;

public class Path {

    private final List<Direction> path;

    public Path(List<Direction> path) {
        this.path = path;
    }

    public List<Direction> getPath() {
        return List.copyOf(path);
    }
}
