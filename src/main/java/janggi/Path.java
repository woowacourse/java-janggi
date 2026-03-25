package janggi;

import java.util.List;

public class Path {

    private final List<Position> path;

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
