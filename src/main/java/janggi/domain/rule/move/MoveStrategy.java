package janggi.domain.rule.move;

import janggi.domain.Position;

public interface MoveStrategy {
    void validateCorrectRule(final Position departure, final Position destination);
}
