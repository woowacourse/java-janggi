package domain.piece;

import domain.board.Side;
import domain.rule.BoardBoundaryRule;
import domain.rule.BasicCaptureRule;
import domain.strategy.OrthogonalLongStepStrategy;

import java.util.List;

public final class Chariot extends Piece {

    public Chariot(Side side) {
        super(
                PieceType.CHARIOT,
                side,
                new OrthogonalLongStepStrategy(),
                List.of(
                        new BoardBoundaryRule(),
                        new BasicCaptureRule()
                )
        );
    }
}
