package domain.position;

import java.util.List;

public record Path(Position src, Position dest, List<Position> waypoints) {

    public Path(Position src, Position dest, List<Position> waypoints) {
        this.src = src;
        this.dest = dest;
        this.waypoints = List.copyOf(waypoints);
    }
}
