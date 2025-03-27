package janggi.domain.rule.move;

import janggi.domain.Position;

public interface MoveStrategy {
    void validateCorrectRule(Position departure, Position destination);
}
