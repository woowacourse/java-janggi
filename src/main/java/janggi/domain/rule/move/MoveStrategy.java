package janggi.domain.rule.move;

import janggi.domain.Position;
import janggi.domain.rule.Movement;

public interface MoveStrategy {
    void validateCorrectRule(final Position departure, final Position destination, final Movement movement);
}
