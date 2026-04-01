package domain.piece;

import domain.board.Side;
import domain.coordinate.Direction;
import domain.strategy.ForwardStepStrategy;

import java.util.List;

public final class Pawn extends Piece {

    public Pawn(Side side) {
        super(
                PieceType.PAWN,
                side,
                new ForwardStepStrategy(
                        List.of(
                                Direction.getForward(side),
                                Direction.LEFT,
                                Direction.RIGHT)
                )
        );
    }
}
