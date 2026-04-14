package domain.piece;

import domain.Side;
import domain.rule.Rule;
import domain.rule.StepRule;
import domain.strategy.PalaceStrategy;
import domain.strategy.StepStrategy;
import domain.strategy.Strategy;

public final class King extends Piece {

    private final Strategy strategy = new PalaceStrategy(new StepStrategy());
    private final Rule rule = new StepRule();

    public King(Side side) {
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
