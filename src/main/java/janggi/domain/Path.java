package janggi.domain;

import janggi.domain.position.Position;

import java.util.List;
import java.util.Objects;

public class Path {
    private final WayPoints wayPoints;
    private final Position destination;

    private Path(WayPoints wayPoints, Position destination) {
        this.wayPoints = wayPoints;
        this.destination = destination;
    }

    public static Path of(Position position){
        return new Path(new WayPoints(List.of()), position);
    }

    public static Path of(List<Position> wayPoints, Position destination){
        return new Path(new WayPoints(wayPoints), destination);
    }

    public Position destination() {
        return destination;
    }

    public boolean isDestination(Position position) {
        return destination.equals(position);
    }

    public boolean isOnWayPoints(Position position) {
        return wayPoints.isBlocked(position);
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Path path)) return false;

        return Objects.equals(wayPoints, path.wayPoints) && Objects.equals(destination, path.destination);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(wayPoints);
        result = 31 * result + Objects.hashCode(destination);
        return result;
    }
}
