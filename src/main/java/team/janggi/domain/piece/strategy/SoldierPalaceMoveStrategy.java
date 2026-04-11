package team.janggi.domain.piece.strategy;

import team.janggi.domain.Palace;
import team.janggi.domain.Position;
import team.janggi.domain.board.BoardStateReader;

public class SoldierPalaceMoveStrategy extends SoldierMoveStrategy {
    public final static SoldierPalaceMoveStrategy towardTopInstance = new SoldierPalaceMoveStrategy(ForwardDirection.TOWARD_TOP);
    public final static SoldierPalaceMoveStrategy towardBottomInstance = new SoldierPalaceMoveStrategy(ForwardDirection.TOWARD_BOTTOM);

    private final ForwardDirection forwardDirection;

    public SoldierPalaceMoveStrategy(ForwardDirection direction) {
        super(direction);
        forwardDirection = direction;
    }

    @Override
    public boolean calculateMove(Position from, Position to, BoardStateReader stateReader) {
        if (super.calculateMove(from, to, stateReader)) {
            return true;
        }

        return isDiagonalMove(from, to);
    }

    private boolean isDiagonalMove(Position from, Position to) {
        if (!Palace.isInPalace(from) || !Palace.isInPalace(to)) {
            return false;
        }
        if (forwardDirection == ForwardDirection.TOWARD_TOP) {
            return from.y() - to.y() == 1 && Math.abs(from.x() - to.x()) == 1;
        }

        if (forwardDirection == ForwardDirection.TOWARD_BOTTOM) {
            return to.y() - from.y() == 1 && Math.abs(from.x() - to.x()) == 1;
        }

        return false;
    }
}
