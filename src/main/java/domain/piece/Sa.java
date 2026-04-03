package domain.piece;

import domain.piece.policy.MovementPolicy;
import domain.piece.strategy.MoveStrategy;

public class Sa extends Piece {
    private static final double SA_SCORE = 3;

    public Sa(MoveStrategy moveStrategy, MovementPolicy movementPolicy, Team team) {
        super(moveStrategy, movementPolicy, PieceType.SA, team, SA_SCORE);
    }
}