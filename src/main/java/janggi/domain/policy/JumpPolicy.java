package janggi.domain.policy;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.BoardInfo;
import janggi.domain.piece.PieceType;
import janggi.domain.Route;

public class JumpPolicy implements RoutePolicy {
    @Override
    public boolean isMovable(Route route, Side side, BoardInfo boardInfo) {
        boolean hasPo = route.isAnyBetween(position -> isPo(position, boardInfo)) || route.isDestinationSatisfied(position -> isPo(position, boardInfo));
        if (hasPo) {
            return false;
        }

        return route.countBetween(position -> !boardInfo.isEmpty(position)) == 1 && route.isDestinationSatisfied(position -> !boardInfo.isAlly(side, position));
    }

    private boolean isPo(Position position, BoardInfo boardInfo) {
        return boardInfo.isEqualPieceType(position, PieceType.PO);
    }
}
