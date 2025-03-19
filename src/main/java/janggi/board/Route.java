package janggi.board;

import java.util.ArrayDeque;
import java.util.Deque;

public class Route {

    private final Deque<Position> positions;

    public Route() {
        this.positions = new ArrayDeque<>();
    }

    public void addRoute(final Position position) {
        positions.add(position);
    }

    public Position getDestination() {
        return positions.getLast();
    }

    public Deque<Position> getPositions() {
        return positions;
    }

}
