package janggi.model.movement.patternBasedMovement;

import janggi.model.movement.pattern.MovementPattern;
import janggi.model.movement.pattern.MovementPatterns;
import janggi.model.position.relative.RelativePosition;
import java.util.List;

public class ByeongMovement extends PatternBasedMovement {

    private static final MovementPatterns PATTERNS = MovementPatterns.of(List.of(
            MovementPattern.of(List.of(new RelativePosition(1, 0))),
            MovementPattern.of(List.of(new RelativePosition(-1, 0))),
            MovementPattern.of(List.of(new RelativePosition(0, 1))),
            MovementPattern.of(List.of(new RelativePosition(0, -1)))
    ));

    private ByeongMovement(MovementPatterns patterns) {
        super(patterns);
    }

    public ByeongMovement() {
        this(PATTERNS);
    }
}
