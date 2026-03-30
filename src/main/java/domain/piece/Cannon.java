package domain.piece;

import domain.board.Side;
import domain.rule.BoardBoundaryRule;
import domain.rule.CannonCaptureRule;
import domain.rule.BasicCaptureRule;
import domain.strategy.CannonMoveStrategy;

import java.util.List;

public final class Cannon extends Piece {

    public Cannon(Side side) {
        super(
                PieceType.CANNON,
                side,
                new CannonMoveStrategy(),
                List.of(
                        new BoardBoundaryRule(),
                        new BasicCaptureRule(),
                        new CannonCaptureRule()
                )
        );
    }
}
