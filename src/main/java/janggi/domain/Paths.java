package janggi.domain;

import java.util.List;

public class Paths {

    private final List<Path> paths;

    public Paths(List<Path> paths) {
        this.paths = paths;
    }

    public void addPath(Path path) {
        paths.add(path);
    }
}
