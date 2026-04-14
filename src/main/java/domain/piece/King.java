package domain.piece;

import domain.Side;
import domain.rule.StepRule;
import domain.strategy.PalaceStrategy;
import domain.strategy.StepStrategy;

public final class King extends Piece {

    public King(Side side) {
        super(side, new PalaceStrategy(new StepStrategy()), new StepRule());
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }

    @Override
    public Piece withSide(Side side) {
        return new King(side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
