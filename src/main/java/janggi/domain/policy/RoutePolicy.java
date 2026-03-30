package janggi.domain.policy;

import janggi.domain.Side;
import janggi.domain.board.BoardInfo;
import janggi.domain.Route;

public interface RoutePolicy {
    boolean isMovable(Route route, Side side, BoardInfo boardInfo);
}
