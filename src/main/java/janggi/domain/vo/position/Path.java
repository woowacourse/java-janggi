package janggi.domain.vo.position;

import janggi.domain.Direction;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private final List<Position> positions;

    public Path() {
        this(new ArrayList<>());
    }

    public Path(List<Position> positions) {
        this.positions = new ArrayList<>(positions);
    }

    public boolean isArrived(Position position) {
        return !positions.isEmpty() && positions.getLast().equals(position);
    }

    public int destinationStep() {
        return positions.size() - 1;
    }

    public Position positionAt(int index) {
        return positions.get(index);
    }

    public boolean contains(Position position) {
        return positions.contains(position);
    }

    public static Path createByDirections(Position from, List<Direction> directions) {
        List<Position> candidates = new ArrayList<>();

        for (Direction direction : directions) {
            if (from.hasNext(direction)) {
                candidates.add(from.nextPosition(direction));
            }
        }

        return new Path(candidates);
    }

    public static Path between(Position from, Position to) {
        List<Position> path = new ArrayList<>();
        Direction direction = Direction.between(from, to);
        Position pathPosition = from;

        while(!pathPosition.equals(to)) {
            path.add(pathPosition);
            pathPosition = pathPosition.nextPosition(direction);
        }

        return new Path(path);
    }

    public static int countOfPositionBetween(Position from, Position to) {
        return Path.between(from, to).size();
    }

    public List<Position> getPositions() {
        return List.copyOf(positions);
    }

    public int size() {
        return positions.size();
    }
}
