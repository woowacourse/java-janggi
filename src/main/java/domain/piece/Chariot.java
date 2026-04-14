package domain.piece;

import domain.rule.Rule;
import domain.rule.SlidingRule;
import domain.Side;
import domain.strategy.LinearStrategy;
import domain.strategy.Strategy;

public final class Chariot extends Piece {

    private final Strategy strategy = new LinearStrategy();
    private final Rule rule = new SlidingRule();

    public Chariot(Side side) {
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
