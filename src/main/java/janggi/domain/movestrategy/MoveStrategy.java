package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import java.util.List;

public interface MoveStrategy {
    boolean canMoveByBasicMovingRule(Position from, Position to);

    List<Position> findPath(Position from, Position to);
}
