package domain.piece;

import domain.rule.SlidingRule;
import domain.Side;
import domain.strategy.LinearStrategy;

public final class Chariot extends Piece {

    public Chariot(Side side) {
        super(side, new LinearStrategy(), new SlidingRule());
    }

    @Override
    public PieceType getType() {
        return PieceType.CHARIOT;
    }

    @Override
    public Piece withSide(Side side) {
        return new Chariot(side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
