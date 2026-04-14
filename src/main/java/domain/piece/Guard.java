package domain.piece;


import domain.coordinate.Position;
import domain.Side;
import domain.rule.StepRule;
import domain.strategy.PalaceStrategy;
import domain.strategy.StepStrategy;

import java.util.List;

public final class Guard extends Piece {

    public Guard(Side side) {
        super(side, new PalaceStrategy(new StepStrategy()), new StepRule());
    }

    @Override
    public PieceType getType() {
        return PieceType.GUARD;
    }

    @Override
    public Piece withSide(Side side) {
        return new Guard(side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
