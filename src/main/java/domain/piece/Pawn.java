package domain.piece;

import domain.board.Side;
import domain.rule.BoardBoundaryRule;
import domain.rule.BasicCaptureRule;
import domain.strategy.ForwardStepStrategy;

import java.util.List;

public final class Pawn extends Piece {

    public Pawn(Side side) {
        super(
                PieceType.PAWN,
                side,
                new ForwardStepStrategy(),
                List.of(
                        new BoardBoundaryRule(),
                        new BasicCaptureRule()
                )
        );
    }
}
