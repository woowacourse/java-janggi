package domain.piece;

import domain.Side;
import domain.rule.Rule;
import domain.rule.StepRule;
import domain.strategy.ForwardStrategy;
import domain.strategy.StepStrategy;
import domain.strategy.Strategy;

public final class Pawn extends Piece {

    private final Strategy strategy;
    private final Rule rule = new StepRule();

    public Pawn(Side side) {
        super(side);
        this.strategy = new ForwardStrategy(new StepStrategy(), forward());
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
