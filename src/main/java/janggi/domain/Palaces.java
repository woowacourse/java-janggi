package janggi.domain;

import janggi.domain.position.Direction;
import janggi.domain.position.Position;

import java.util.List;
import java.util.Optional;

public class Palaces {
    private static final Palaces STANDARD = new Palaces(List.of(Palace.cho(), Palace.han()));

    private final List<Palace> elements;

    private Palaces(List<Palace> elements) {
        this.elements = elements;
    }

    public static Palaces of() {
        return STANDARD;
    }

    public List<Direction> diagonalDirectionsAt(Position position) {
        return findPalace(position)
                .map(palace -> palace.diagonalDirectionsAt(position))
                .orElse(List.of());
    }

    public boolean containsAny(Position position) {
        return findPalace(position).isPresent();
    }

    private Optional<Palace> findPalace(Position position) {
        return elements.stream()
                .filter(palace -> palace.contains(position))
                .findFirst();
    }
}
