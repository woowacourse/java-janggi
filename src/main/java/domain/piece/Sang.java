package domain.piece;

import domain.piece.policy.MovementPolicy;
import domain.piece.strategy.MoveStrategy;

public class Sang extends Piece {
    private static final double SANG_SCORE = 3;

    public Sang(MoveStrategy moveStrategy, MovementPolicy movementPolicy, Team team) {
        super(moveStrategy, movementPolicy, PieceType.SANG, team, SANG_SCORE);
    }
}