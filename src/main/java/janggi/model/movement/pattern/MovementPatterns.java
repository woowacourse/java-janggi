package janggi.model.movement.pattern;

import janggi.model.position.absolute.Position;
import java.util.List;
import java.util.Optional;

public class MovementPatterns {

    private final List<MovementPattern> patterns;

    private MovementPatterns(List<MovementPattern> patterns) {
        this.patterns = patterns;
    }

    public static MovementPatterns of(List<MovementPattern> patterns) {
        return new MovementPatterns(List.copyOf(patterns));
    }

    public Optional<MovementPattern> findMatchingPattern(
            Position from,
            Position to
    ) {
        return patterns.stream()
                .filter(pattern -> pattern.isMatchedWith(from, to))
                .findFirst();
    }
}
