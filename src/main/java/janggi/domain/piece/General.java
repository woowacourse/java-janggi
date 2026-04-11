package janggi.domain.piece;

import janggi.domain.Side;
import janggi.domain.move.MovementStrategy;
import janggi.domain.space.Palace;
import janggi.domain.space.Position;

public class General extends Piece {
    public General(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public boolean isWithinBoundary(Position position) {
        return Palace.isInside(position, this.getSide());
    }

    @Override
    public boolean isVital() {
        return true;
    }

    @Override
    public PieceType getType() {
        return PieceType.GENERAL;
    }
}
