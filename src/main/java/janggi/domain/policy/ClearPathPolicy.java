package janggi.domain.policy;

import janggi.domain.Side;
import janggi.domain.board.BaseBoard;
import janggi.domain.Route;

public class ClearPathPolicy implements RoutePolicy {
    @Override
    public boolean isMovable(Route route, Side side, BaseBoard boardInfo) {
        return route.isEveryBetween(boardInfo::isEmpty) && route.isDestinationSatisfied(position -> !boardInfo.isAlly(side, position));
    }
}
