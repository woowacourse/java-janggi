package janggi.board;

import java.util.Deque;

public class Route {

    private final Deque<Position> positions;

    public Route(final Deque<Position> positions) {
        this.positions = positions;
    }

    public Position getDestination() {
        return positions.getLast();
    }

    public Deque<Position> getPositions() {
        return positions;
    }
}
