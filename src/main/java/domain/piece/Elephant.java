package domain.piece;

import domain.coordinate.Direction;
import domain.coordinate.DirectionSequence;
import domain.Side;
import domain.rule.LeapRule;
import domain.rule.Rule;
import domain.strategy.SequenceStrategy;
import domain.strategy.Strategy;

import java.util.List;

public final class Elephant extends Piece {

    private static final List<DirectionSequence> SEQUENCES = List.of(
            DirectionSequence.of(Direction.UP, Direction.UP_LEFT, Direction.UP_LEFT),
            DirectionSequence.of(Direction.UP, Direction.UP_RIGHT, Direction.UP_RIGHT),
            DirectionSequence.of(Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_LEFT),
            DirectionSequence.of(Direction.DOWN, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT),
            DirectionSequence.of(Direction.LEFT, Direction.UP_LEFT, Direction.UP_LEFT),
            DirectionSequence.of(Direction.LEFT, Direction.DOWN_LEFT, Direction.DOWN_LEFT),
            DirectionSequence.of(Direction.RIGHT, Direction.UP_RIGHT, Direction.UP_RIGHT),
            DirectionSequence.of(Direction.RIGHT, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT));

    private final Strategy strategy = new SequenceStrategy(SEQUENCES);
    private final Rule rule = new LeapRule();

    public Elephant(Side side) {
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
        return PieceType.ELEPHANT;
    }

    @Override
    public Piece withSide(Side side) {
        return new Elephant(side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
