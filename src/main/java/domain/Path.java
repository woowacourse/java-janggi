package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Path {
    private final List<Position> path = new ArrayList<>();

    public void add(Position position) {
        path.add(position);
    }

    public List<Position> getPath() {
        return List.copyOf(path);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Path otherPath = (Path) o;
        return Objects.equals(path, otherPath.path);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(path);
    }
}
