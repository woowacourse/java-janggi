package janggi.model.position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PositionPath {


    private final List<Position> path;

    public PositionPath(List<Position> path) {
        this.path = path;
    }

    public Position getFirst() {
        return path.getFirst();
    }

    public Position getLast() {
        return path.getLast();
    }

    public Stream<Position> stream() {
        return path.stream();
    }

    public PositionPath removeFirstAndLast() {
        int startIndexOffset = 1;
        int endIndexOffset = 1;
        return new PositionPath(path.subList(startIndexOffset, path.size() - endIndexOffset));
    }

    public PositionPath addFirstAndLast(Position first, Position last) {
        List<Position> result = new ArrayList<>(path);

        if (!path.isEmpty()) {
            validateContinuity(first, last);
        }

        result.addFirst(first);
        result.addLast(last);

        return new PositionPath(result);
    }

    private void validateContinuity(Position first, Position last) {
        Position nextPosition = path.getFirst();

        int firstRowDistance = Math.abs(nextPosition.getRowDistance(first));
        int firstColumnDistance = Math.abs(nextPosition.getColumnDistance(first));

        if (firstRowDistance > 2 || firstColumnDistance > 2) {
            throw new IllegalArgumentException("연결할 수 없습니다.");
        }

        Position previousPosition = path.getLast();

        int lastColumnDistance = Math.abs(previousPosition.getColumnDistance(last));
        int lastRowDistance = Math.abs(previousPosition.getRowDistance(last));

        if (lastColumnDistance > 2 || lastRowDistance > 2) {
            throw new IllegalArgumentException("연결할 수 없습니다.");
        }
    }

    public PositionPath concatenate(PositionPath other) {
        List<Position> firstPath = this.path;
        List<Position> secondPath = other.path;

        if (!firstPath.getLast().equals(secondPath.getFirst())) {
            throw new IllegalArgumentException("연결할 수 없습니다.");
        }

        int indexAfterFirst = 1;

        List<Position> result = new ArrayList<>(firstPath);
        result.addAll(secondPath.subList(indexAfterFirst, secondPath.size()));

        return new PositionPath(result);
    }

}
