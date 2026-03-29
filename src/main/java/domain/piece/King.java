package domain.piece;

import domain.board.Side;
import domain.rule.BoardBoundaryRule;
import domain.rule.BasicCaptureRule;
import domain.strategy.OrthogonalStepStrategy;

import java.util.List;

public final class King extends Piece {

    public King(Side side) {
        super(
                PieceType.KING,
                side,
                new OrthogonalStepStrategy(),
                List.of(
                        new BoardBoundaryRule(),
                        new BasicCaptureRule()
                )
        );
    }

    @Override
    public boolean isCannon() {
        return false;
    }
}
