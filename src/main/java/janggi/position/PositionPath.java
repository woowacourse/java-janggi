package janggi.position;

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
        path.addAll(second.path.subList(1, second.path.size()));

        return new PositionPath(path);
    }

    public PositionPath(List<Position> path) {
        validate(path);
        this.path = path;
    }

    private void validate(List<Position> path) {
        if (path.isEmpty()) {
            throw new IllegalArgumentException("빈 경로입니다.");
        }
    }

    public Position getDestination() {
        return path.getLast();
    }

    public PositionPath getCourse() {
        int startIdx = 0;
        int toIdx = path.size() - 1;

        return new PositionPath(path.subList(startIdx, toIdx));
    }

    public Stream<Position> stream() {
        return path.stream();
    }
}
