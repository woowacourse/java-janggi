package janggi.domain.route;

import janggi.domain.board.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Destinations {

    private final List<Position> destinations;

    public Destinations() {
        this.destinations = Collections.emptyList();
    }

    public Destinations(List<Position> destinations) {
        this.destinations = new ArrayList<>(destinations);
    }

    public Destinations addDestination(Position destination) {
        List<Position> newDestinations = new ArrayList<>(this.destinations);
        newDestinations.add(destination);
        return new Destinations(newDestinations);
    }

    public Destinations addDestinations(Destinations other) {
        List<Position> newDestinations = new ArrayList<>(this.destinations);
        newDestinations.addAll(other.destinations);
        return new Destinations(newDestinations);
    }

    public List<Position> getDestinations() {
        return List.copyOf(this.destinations);
    }

    public boolean isEmpty() {
        return this.destinations.isEmpty();
    }

    public boolean containsDestination(Position destination) {
        return this.destinations.contains(destination);
    }
}
