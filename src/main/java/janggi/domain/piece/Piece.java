package janggi.domain.piece;

import janggi.domain.Route;
import janggi.domain.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Piece {

    protected Position position;

    protected final Team team;

    protected abstract Set<Position> calculateRoute();

    protected abstract List<RawPosition> calculateDestinations();

    public Piece(final Position position, final Team team) {
        this.position = position;
        this.team = team;
    }

    public void move(final Position position) {
        this.position = new Position(position.x(), position.y());
    }

    public Set<Route> calculateRoutes() {
        final List<Position> destinations = new ArrayList<>();

        final List<RawPosition> rawPositions = calculateDestinations();
        for (RawPosition rawPosition : rawPositions) {
            try {
                destinations.add(new Position(rawPosition.x(), rawPosition.y()));
            } catch (IllegalArgumentException e) {
                continue;
            }
        }
        return destinations.stream()
                .map(destination -> new Route(calculateRoute(), destination))
                .collect(Collectors.toSet());
    }

    public boolean isSamePosition(final Position otherPosition) {
        return position.equals(otherPosition);
    }
}
