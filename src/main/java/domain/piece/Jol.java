package domain.piece;

import domain.piece.policy.MovementPolicy;
import domain.piece.strategy.MoveStrategy;

public class Jol extends Piece {
    private static final double JOL_SCORE = 2;

    public Jol(MoveStrategy moveStrategy, MovementPolicy movementPolicy, Team team) {
        super(moveStrategy, movementPolicy, PieceType.JOL, team, JOL_SCORE);
    }
}