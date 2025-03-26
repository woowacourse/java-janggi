package janggi.model;

import java.util.List;

public class Path {
    private final Position destinationPosition;
    private final List<Position> cornerPositions;

    public Path(Position destinationPosition, List<Position> cornerPositions) {
        this.destinationPosition = destinationPosition;
        this.cornerPositions = cornerPositions;
    }

    public Position getDestinationPosition() {
        return destinationPosition;
    }

    public List<Position> getCornerPositions() {
        return cornerPositions;
    }
}
