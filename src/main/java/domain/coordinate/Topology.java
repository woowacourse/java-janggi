package domain.coordinate;

import java.util.List;
import java.util.Map;

public class Topology {

    private static final List<Direction> DEFAULT_DIRECTIONS = List.of(
            Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT);

    private final Map<Position, List<Direction>> adjacencyMap;

    public Topology(Map<Position, List<Direction>> adjacencyMap) {
        this.adjacencyMap = adjacencyMap;
    }

    public List<Direction> getDirections(Position position) {
        return adjacencyMap.getOrDefault(position, DEFAULT_DIRECTIONS);
    }
}
