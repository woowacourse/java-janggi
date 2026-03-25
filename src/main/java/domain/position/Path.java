package domain.position;

import java.util.List;

public final class Path {

    private final Position start;
    private final Position end;
    private final List<Position> waypoints;

    public Path(Position start, Position end, List<Position> waypoints) {
        this.start = start;
        this.end = end;
        this.waypoints = List.copyOf(waypoints);
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }

    public List<Position> getWaypoints() {
        return waypoints;
    }
}
