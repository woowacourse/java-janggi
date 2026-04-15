package domain.board;

import domain.place.moveStrategy.Direction;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Palace {

    private static final Palace INSTANCE = new Palace();

    private final Map<Position, Set<Direction>> PALACE_DIRECTIONS_MAP;

    private Palace() {
        PALACE_DIRECTIONS_MAP = new HashMap<>();
        PALACE_DIRECTIONS_MAP.putAll(choSidePalaceMap());
        PALACE_DIRECTIONS_MAP.putAll(hanSidePalaceMap());
    }

    public static Palace getInstance() {
        return INSTANCE;
    }

    private Map<Position, Set<Direction>> choSidePalaceMap() {
        return Map.of(
                new Position(8, 4), Set.of(Direction.RIGHT_TOP),
                new Position(8, 5), Set.of(),
                new Position(8, 6), Set.of(Direction.LEFT_TOP),
                new Position(9, 4), Set.of(),
                new Position(9, 5), Set.of(
                        Direction.RIGHT_TOP, Direction.RIGHT_DOWN, Direction.LEFT_TOP, Direction.LEFT_DOWN),
                new Position(9, 6), Set.of(),
                new Position(10, 4), Set.of(Direction.RIGHT_DOWN),
                new Position(10, 5), Set.of(),
                new Position(10, 6), Set.of(Direction.LEFT_DOWN)
        );
    }

    private Map<Position, Set<Direction>> hanSidePalaceMap() {
        return Map.of(
                new Position(1, 4), Set.of(Direction.RIGHT_TOP),
                new Position(1, 5), Set.of(),
                new Position(1, 6), Set.of(Direction.LEFT_TOP),
                new Position(2, 4), Set.of(),
                new Position(2, 5), Set.of(
                        Direction.RIGHT_TOP, Direction.RIGHT_DOWN, Direction.LEFT_TOP, Direction.LEFT_DOWN),
                new Position(2, 6), Set.of(),
                new Position(3, 4), Set.of(Direction.RIGHT_DOWN),
                new Position(3, 5), Set.of(),
                new Position(3, 6), Set.of(Direction.LEFT_DOWN)
        );
    }

    public boolean isInPalace(Position position) {
        return PALACE_DIRECTIONS_MAP.containsKey(position);
    }

    public boolean isConnected(Position position, Direction direction) {
        return PALACE_DIRECTIONS_MAP
                .getOrDefault(position, Set.of())
                .contains(direction);
    }

    public Set<Direction> findAvailableDirections(Position position) {
        return PALACE_DIRECTIONS_MAP
                .getOrDefault(position, Set.of());
    }

    public Set<Position> findNextPositions(Position position) {
        return PALACE_DIRECTIONS_MAP
                .getOrDefault(position, Set.of())
                .stream()
                .map(position::move)
                .collect(Collectors.toSet());
    }
}
