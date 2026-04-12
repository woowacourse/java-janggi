package domain.piece;

import domain.state.Side;
import domain.coordinate.Direction;
import domain.strategy.ForwardStepStrategy;

import java.util.List;

public final class Pawn extends Piece {

    private static final int SCORE = 2;

    public Pawn(Side side) {
        super(
                PieceType.PAWN,
                side,
                new ForwardStepStrategy(
                        List.of(
                                Direction.getForward(side),
                                Direction.LEFT,
                                Direction.RIGHT
                        ),
                        Direction.getForward(side)
                )
        );
    }

    @Override
    public int getScore() {
        return SCORE;
    }
}
