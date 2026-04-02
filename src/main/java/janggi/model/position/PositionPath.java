package janggi.model.position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PositionPath {

    private final List<Position> path;

    public static PositionPath concatenate(PositionPath first, PositionPath second) {
        List<Position> path = new ArrayList<>();
        if (!first.path.getLast().equals(second.path.getFirst())) {
            throw new IllegalArgumentException("연결할 수 없습니다.");
        }

        path.addAll(first.path);
        int startIndex = 1;
        path.addAll(second.path.subList(startIndex, second.path.size()));

        return new PositionPath(path);
    }

    public PositionPath(List<Position> path) {
        this.path = path;
    }

    public Position getDestination() {
        return path.getLast();
    }

    public Stream<Position> stream() {
        return path.stream();
    }

    public PositionPath getMiddlePath() {
        int startIndexOffset = 1;
        int endIndexOffset = 1;
        return new PositionPath(path.subList(startIndexOffset, path.size() - endIndexOffset));
    }
}
