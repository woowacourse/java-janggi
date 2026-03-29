package janggi.domain.policy;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.BoardInterface;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Route;

public class JumpPolicy implements RoutePolicy {
    @Override
    public boolean isMovable(Route route, Side side, BoardInterface boardInterface) {
        boolean hasPo = route.isAnyBetween(position -> isPo(position, boardInterface)) || route.isDestinationSatisfied(position -> isPo(position, boardInterface));
        if (hasPo) {
            return false;
        }

        return route.countBetween(position -> !boardInterface.isEmpty(position)) == 1 && route.isDestinationSatisfied(position -> !boardInterface.isAlly(side, position));
    }

    private boolean isPo(Position position, BoardInterface boardInterface) {
        return boardInterface.isEqualPieceType(position, PieceType.PO);
    }


    private boolean isMovableLast(Position position, Side side, BoardInterface boardInterface) {
        return !boardInterface.isAlly(side, position);
    }
}
