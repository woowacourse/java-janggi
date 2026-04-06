package janggi.domain.route;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Paths implements Iterable<Path> {

    private final List<Path> paths;

    public Paths() {
        this.paths = new ArrayList<>();
    }

    public void addPath(Path path) {
        if (path.isEmpty() || paths.contains(path)) {
            return;
        }
        paths.add(path);
    }

    @Override
    public Iterator<Path> iterator() {
        return paths.iterator();
    }
}
