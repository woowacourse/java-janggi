package domain.piece;

import domain.Side;
import domain.rule.CannonRule;
import domain.rule.Rule;
import domain.strategy.LinearStrategy;
import domain.strategy.Strategy;

public final class Cannon extends Piece {

    private final Strategy strategy = new LinearStrategy();
    private final Rule rule = new CannonRule();

    public Cannon(Side side) {
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
        return PieceType.CANNON;
    }

    @Override
    public Piece withSide(Side side) {
        return new Cannon(side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
