package domain.piece;

import domain.Palace;
import domain.Position;
import domain.Side;
import domain.strategy.MovementStrategy;

public class Guard extends Piece {
    public Guard(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public boolean isWithinBoundary(Position position) {
        return Palace.isInside(position, this.getSide());
    }

    @Override
    public PieceType getType() {
        return PieceType.GUARD;
    }
}
