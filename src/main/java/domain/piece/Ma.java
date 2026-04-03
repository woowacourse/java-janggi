package domain.piece;

import domain.piece.policy.MovementPolicy;
import domain.piece.strategy.MoveStrategy;

public class Ma extends Piece {
    private static final double MA_SCORE = 5;

    public Ma(MoveStrategy moveStrategy, MovementPolicy movementPolicy, Team team) {
        super(moveStrategy, movementPolicy, PieceType.MA, team, MA_SCORE);
    }
}