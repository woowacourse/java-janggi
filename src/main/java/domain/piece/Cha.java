package domain.piece;

import domain.piece.policy.MovementPolicy;
import domain.piece.strategy.MoveStrategy;

public class Cha extends Piece {
    private static final double CHA_SCORE = 13;

    public Cha(MoveStrategy moveStrategy, MovementPolicy movementPolicy, Team team) {
        super(moveStrategy, movementPolicy, PieceType.CHA, team, CHA_SCORE);
    }
}