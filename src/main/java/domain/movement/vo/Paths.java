package domain.movement.vo;

import java.util.List;

public final class Paths {
    private final List<Path> paths;

    public Paths(List<Path> paths) {
        this.paths = List.copyOf(paths);
    }

    public List<Path> asList() {
        return paths;
    }
}
