package janggi.value;

import java.util.ArrayList;
import java.util.List;

public final class RelativePath {

    private final List<RelativePosition> positions;

    public RelativePath(List<Direction> directions) {
        positions = directions.stream()
                .map(Direction::getRelativePosition)
                .toList();
    }

    public Path calculatePath(Position originPosition) {
        ArrayList<Position> path = new ArrayList<>();
        Position positionInPath = originPosition;
        for (RelativePosition relativePosition : positions) {
            positionInPath = relativePosition.calculateAbsolutePosition(positionInPath);
            path.add(positionInPath);
        }
        return new Path(path);
    }

    public Position getDestination(Position originPosition) {
        RelativePosition totalRelativePosition = RelativePosition.calculateTotal(positions);
        return totalRelativePosition.calculateAbsolutePosition(originPosition);
    }

    @Override
    public String toString() {
        return "RelativeRoute{" +
                "positions=" + positions +
                '}';
    }

}
