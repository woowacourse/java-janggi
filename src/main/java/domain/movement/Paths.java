package domain.movement;

import domain.board.Position;
import java.util.ArrayList;
import java.util.List;

public final class Paths {
    private final List<Path> paths;

    public Paths(List<Path> paths) {
        this.paths = List.copyOf(paths);
    }

    public static Paths empty() {
        return new Paths(new ArrayList<>());
    }

    public Paths add(Path path) {
        List<Path> newPaths = new ArrayList<>(paths);
        newPaths.add(path);
        return new Paths(newPaths);
    }

    public boolean hasPathEndingAt(Position position) {
        return paths.stream().anyMatch(path -> path.endsAt(position));
    }

    public boolean hasPathContaining(Position position) {
        return paths.stream().anyMatch(path -> path.contains(position));
    }

    public List<Path> asList() {
        return paths;
    }
}
