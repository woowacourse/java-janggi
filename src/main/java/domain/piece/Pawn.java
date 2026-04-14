package domain.piece;

import domain.Side;
import domain.rule.StepRule;
import domain.strategy.ForwardStrategy;
import domain.strategy.StepStrategy;

public final class Pawn extends Piece {

    public Pawn(Side side) {
        super(side, new ForwardStrategy(new StepStrategy(), side.getForward()), new StepRule());
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }

    @Override
    public Piece withSide(Side side) {
        return new Pawn(side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
