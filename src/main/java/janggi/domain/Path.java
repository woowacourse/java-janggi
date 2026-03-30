package janggi.domain;

import janggi.domain.position.Position;

import java.util.List;

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
}
