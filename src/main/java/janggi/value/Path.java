package janggi.value;

import java.util.List;

public final class Path {

    private final Position start;
    private final List<Position> middle;
    private final Position end;

    public Path(List<Position> positions) {
        this.start = positions.getFirst();
        this.middle = positions.subList(1, positions.size() - 1);
        this.end = positions.getLast();
    }

    public boolean isInMiddle(Position position) {
        return middle.contains(position);
    }

    public Position getStart() {
        return start;
    }

    public List<Position> getMiddle() {
        return middle;
    }

    public Position getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Path{" +
                "start=" + start +
                ", middle=" + middle +
                ", end=" + end +
                '}';
    }
}
