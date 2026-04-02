package janggi.model.movement.patternBasedMovement;

import janggi.model.movement.pattern.MovementPattern;
import janggi.model.movement.pattern.MovementPatterns;
import janggi.model.position.relative.RelativePosition;
import java.util.List;

public class SangMovement extends PatternBasedMovement {

    private static final MovementPatterns PATTERNS = MovementPatterns.of(List.of(
            // 위쪽
            MovementPattern.of(List.of(
                    new RelativePosition(1, 0),
                    new RelativePosition(1, -1),
                    new RelativePosition(1, -1)
            )),
            MovementPattern.of(List.of(
                    new RelativePosition(1, 0),
                    new RelativePosition(1, 1),
                    new RelativePosition(1, 1)
            )),

            // 아래쪽
            MovementPattern.of(List.of(
                    new RelativePosition(-1, 0),
                    new RelativePosition(-1, -1),
                    new RelativePosition(-1, -1)
            )),
            MovementPattern.of(List.of(
                    new RelativePosition(-1, 0),
                    new RelativePosition(-1, 1),
                    new RelativePosition(-1, 1)
            )),

            // 오른쪽
            MovementPattern.of(List.of(
                    new RelativePosition(0, 1),
                    new RelativePosition(1, 1),
                    new RelativePosition(1, 1)
            )),
            MovementPattern.of(List.of(
                    new RelativePosition(0, 1),
                    new RelativePosition(-1, 1),
                    new RelativePosition(-1, 1)
            )),

            // 왼쪽
            MovementPattern.of(List.of(
                    new RelativePosition(0, -1),
                    new RelativePosition(1, -1),
                    new RelativePosition(1, -1)
            )),
            MovementPattern.of(List.of(
                    new RelativePosition(0, -1),
                    new RelativePosition(-1, -1),
                    new RelativePosition(-1, -1)
            ))
    ));

    private SangMovement(MovementPatterns patterns) {
        super(patterns);
    }

    public SangMovement() {
        this(PATTERNS);
    }
}
