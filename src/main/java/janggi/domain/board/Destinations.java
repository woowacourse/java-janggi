package janggi.domain.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Destinations {

    private final List<Position> destinations;

    private Destinations(List<Position> destinations) {
        this.destinations = new ArrayList<>(destinations);
    }

    public static Destinations empty() {
        return new Destinations(Collections.emptyList());
    }

    public static Destinations of(List<Position> destinations) {
        return new Destinations(destinations);
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

    public Destinations retainDestination(Destinations destinations) {
        List<Position> copyDestinations = new ArrayList<>(this.destinations);
        copyDestinations.retainAll(destinations.destinations);
        return new Destinations(copyDestinations);
    }
}
