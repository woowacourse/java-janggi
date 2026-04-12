package domain.piece;

import domain.piece.policy.MovementPolicy;
import domain.piece.strategy.MoveStrategy;
import java.util.List;

public class Ma extends Piece {
    private static final double MA_SCORE = 5;

    public Ma(MoveStrategy moveStrategy, List<MovementPolicy> movementPolicy, Team team) {
        super(moveStrategy, movementPolicy, team, PieceType.MA, MA_SCORE);
    }
}