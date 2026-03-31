package janggi.domain.policy;

import janggi.domain.Side;
import janggi.domain.board.BaseBoard;
import janggi.domain.Route;

public interface RoutePolicy {
    boolean isMovable(Route route, Side side, BaseBoard boardInfo);
}
