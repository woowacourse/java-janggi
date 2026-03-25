package janggi.domain.strategy;

import janggi.domain.BoardInterface;
import janggi.domain.Position;
import janggi.domain.Side;

import java.util.List;

public interface MoveStrategy {
    boolean isMovable(List<Position> path, Side side, BoardInterface boardInterface);
}
