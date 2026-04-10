package janggi.domain.policy;

import janggi.domain.board.BaseBoard;
import janggi.domain.Position;
import janggi.domain.Side;

import java.util.List;

public interface RoutePolicy {
    boolean isMovable(List<Position> path, Side side, BaseBoard baseBoard);
}
