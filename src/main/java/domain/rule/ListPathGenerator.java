package domain.rule;

import domain.direction.Direction;
import domain.position.Path;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListPathGenerator implements PathGenerator {

    private final List<List<Direction>> paths;

    public ListPathGenerator(List<List<Direction>> paths) {
        this.paths = paths;
    }

    @Override
    public Path calculatePath(Position src, Position dest) {
        return paths.stream()
                .map(directionPath -> tryBuildPath(src, dest, directionPath))
                .filter(Optional::isPresent)
                .findFirst()
                .flatMap(optional -> optional)
                .orElseThrow(() -> new IllegalArgumentException("목적지로 이동할 수 없습니다."));
    }

    private Optional<Path> tryBuildPath(Position src, Position dest, List<Direction> directionPath) {
        List<Position> waypoints = new ArrayList<>();
        Position current = src;
        for (Direction direction : directionPath) {
            current = direction.move(current);
            waypoints.add(current);
        }

        if (dest.equals(current)) {
            waypoints.removeLast();
            return Optional.of(new Path(src, dest, waypoints));
        }
        return Optional.empty();
    }
}


