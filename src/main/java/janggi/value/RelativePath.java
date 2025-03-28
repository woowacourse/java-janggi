package janggi.value;

import java.util.List;

public final class RelativePath {

    private final List<RelativePosition> positions;

    public RelativePath(List<RelativePosition> positions) {
        this.positions = positions;
    }

    public Path calculatePath(Position originPosition) {
        List<Position> positionsInPath = positions.stream()
                .map(position -> position.covertAbsolutePosition(originPosition))
                .toList();
        return new Path(positionsInPath);
    }

    public Position getDestination(Position originPosition) {
        return positions.getLast().covertAbsolutePosition(originPosition);
    }

    @Override
    public String toString() {
        return "RelativeRoute{" +
                "positions=" + positions +
                '}';
    }
}
