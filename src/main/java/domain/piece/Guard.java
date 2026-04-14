package domain.piece;

import domain.Side;
import domain.rule.Rule;
import domain.rule.StepRule;
import domain.strategy.PalaceStrategy;
import domain.strategy.StepStrategy;
import domain.strategy.Strategy;

public final class Guard extends Piece {

    private final Strategy strategy = new PalaceStrategy(new StepStrategy());
    private final Rule rule = new StepRule();

    public Guard(Side side) {
        super(side);
    }

    @Override
    protected Strategy getStrategy() {
        return strategy;
    }

    @Override
    protected Rule getRule() {
        return rule;
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
