package janggi.domain.rule.move;

import janggi.domain.Position;
import janggi.domain.rule.Movement;

public interface MoveStrategy {
    void validateCorrectRule(Position departure, Position destination, Movement movement);
}
