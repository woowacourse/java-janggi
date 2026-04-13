package domain;

import java.util.*;

public class Palace {

    private static final Palace INSTANCE = new Palace();
    private final Map<Position, Set<Direction>> directions = new HashMap<>();

    private Palace() {
        setUpCho();
        setUpHan();
    }

    public static Palace getInstance() {
        return INSTANCE;
    }

    public Set<Direction> getDirections(Position position) {
        return directions.getOrDefault(position, Set.of());
    }

    public boolean isInPalace(Position position) {
        return directions.containsKey(position);
    }

    public List<Position> findDiagonalRoutes(Position source) {
        List<Position> routes = new ArrayList<>();
        for (Direction direction : List.of(Direction.UP_LEFT, Direction.UP_RIGHT,
                Direction.DOWN_LEFT, Direction.DOWN_RIGHT)) {
            Position current = source;
            while (current.canMove(direction.getX(), direction.getY())) {
                Position next = current.createPosition(direction.getX(), direction.getY());
                if (!isInPalace(next)) {
                    break;
                }
                routes.add(next);
                current = next;
            }
        }
        return routes;
    }

    public List<Position> findDiagonalPathTo(Position source, Position target) {
        List<Position> path = new ArrayList<>();
        Direction direction = source.directionTo(target);
        Position current = source;
        while (!current.equals(target)) {
            current = current.createPosition(direction.getX(), direction.getY());
            if (current.equals(target)) {
                break;
            }
            path.add(current);
        }
        return path;
    }

    private void setUpCho() {
        directions.put(Position.of(4, 8), Set.of(Direction.RIGHT, Direction.DOWN, Direction.DOWN_RIGHT));
        directions.put(Position.of(5, 8), Set.of(Direction.LEFT, Direction.RIGHT, Direction.DOWN));
        directions.put(Position.of(6, 8), Set.of(Direction.LEFT, Direction.DOWN, Direction.DOWN_LEFT));

        directions.put(Position.of(4, 9), Set.of(Direction.UP, Direction.DOWN, Direction.RIGHT));
        directions.put(Position.of(5, 9), Set.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT,
                Direction.UP_LEFT, Direction.UP_RIGHT, Direction.DOWN_LEFT, Direction.DOWN_RIGHT));
        directions.put(Position.of(6, 9), Set.of(Direction.UP, Direction.DOWN, Direction.LEFT));

        directions.put(Position.of(4, 10), Set.of(Direction.UP, Direction.RIGHT, Direction.UP_RIGHT));
        directions.put(Position.of(5, 10), Set.of(Direction.LEFT, Direction.RIGHT, Direction.UP));
        directions.put(Position.of(6, 10), Set.of(Direction.UP, Direction.LEFT, Direction.UP_LEFT));
    }

    private void setUpHan() {
        directions.put(Position.of(4, 1), Set.of(Direction.RIGHT, Direction.DOWN, Direction.DOWN_RIGHT));
        directions.put(Position.of(5, 1), Set.of(Direction.LEFT, Direction.RIGHT, Direction.DOWN));
        directions.put(Position.of(6, 1), Set.of(Direction.LEFT, Direction.DOWN, Direction.DOWN_LEFT));

        directions.put(Position.of(4, 2), Set.of(Direction.UP, Direction.DOWN, Direction.RIGHT));
        directions.put(Position.of(5, 2), Set.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT,
                Direction.UP_LEFT, Direction.UP_RIGHT, Direction.DOWN_LEFT, Direction.DOWN_RIGHT));
        directions.put(Position.of(6, 2), Set.of(Direction.UP, Direction.DOWN, Direction.LEFT));

        directions.put(Position.of(4, 3), Set.of(Direction.UP, Direction.RIGHT, Direction.UP_RIGHT));
        directions.put(Position.of(5, 3), Set.of(Direction.LEFT, Direction.RIGHT, Direction.UP));
        directions.put(Position.of(6, 3), Set.of(Direction.UP, Direction.LEFT, Direction.UP_LEFT));
    }
}
