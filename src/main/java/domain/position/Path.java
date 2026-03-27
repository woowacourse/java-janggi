package domain.position;

import java.util.List;

public record Path(Position source, Position destination, List<Position> waypoints) {

    public Path(Position source, Position destination, List<Position> waypoints) {
        this.source = source;
        this.destination = destination;
        this.waypoints = List.copyOf(waypoints);
    }
}
