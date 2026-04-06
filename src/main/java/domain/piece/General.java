package domain.piece;

import domain.Palace;
import domain.Position;
import domain.Side;
import domain.strategy.MovementStrategy;

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
