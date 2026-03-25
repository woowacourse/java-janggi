package janggi;

import java.util.ArrayList;
import java.util.List;

public class Path {

    private final List<Position> path;

    public static Path concatenate(Path first, Path second) {
        List<Position> path = new ArrayList<>();
        if (!first.path.getLast().equals(second.path.getFirst())) {
            throw new IllegalArgumentException("연결할 수 없습니다.");
        }

        path.addAll(first.path);
        path.addAll(second.path.subList(1, second.path.size()));
        return new Path(path);
    }

    public Path(List<Position> path) {
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

    public Path getCourse() {
        int startIdx = 0;
        int toIdx = path.size() - 1;

        return new Path(path.subList(startIdx, toIdx));
    }

}
