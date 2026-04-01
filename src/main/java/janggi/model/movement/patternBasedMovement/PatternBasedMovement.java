package janggi.model.movement.patternBasedMovement;

import janggi.model.movement.Movement;
import janggi.model.movement.pattern.MovementPatterns;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.PositionPath;

public abstract class PatternBasedMovement implements Movement {

    protected final MovementPatterns patterns;

    protected PatternBasedMovement(MovementPatterns patterns) {
        this.patterns = patterns;
    }

    @Override
    public PositionPath move(Position from, Position to) {
        return patterns.findMatchingPattern(from, to)
                .map(pattern -> pattern.createPath(from))
                .orElseThrow(() -> new IllegalArgumentException("해당 경로로 이동할 수 없습니다."));
    }
}
