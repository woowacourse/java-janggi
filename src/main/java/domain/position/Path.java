package domain.position;

import java.util.List;

public final class Path {

    private final Position src;
    private final Position dest;
    private final List<Position> waypoints;

    public Path(Position src, Position dest, List<Position> waypoints) {
        this.src = src;
        this.dest = dest;
        this.waypoints = List.copyOf(waypoints);
    }

    public Position getSrc() {
        return src;
    }

    public Position getDest() {
        return dest;
    }

    public List<Position> getWaypoints() {
        return waypoints;
    }
}
