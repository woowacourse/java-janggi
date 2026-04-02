package domain.movement;

import domain.board.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Path> asList() {
        return paths;
    }

    public List<Position> allCandidatePositions() {
        return paths.stream()
                .flatMap(path -> path.positions().stream())
                .distinct()
                .collect(Collectors.toList());
    }
}
