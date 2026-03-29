package janggi.domain.policy;

import janggi.domain.Side;
import janggi.domain.board.BoardInterface;
import janggi.domain.Route;

public class ClearPathPolicy implements RoutePolicy {
    @Override
    public boolean isMovable(Route route, Side side, BoardInterface boardInterface) {
        return route.isEveryBetween(boardInterface::isEmpty) && route.isDestinationSatisfied(position -> !boardInterface.isAlly(side, position));
    }
}
