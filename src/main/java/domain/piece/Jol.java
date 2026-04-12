package domain.piece;

import domain.piece.policy.MovementPolicy;
import domain.piece.strategy.MoveStrategy;
import java.util.List;

public class Jol extends Piece {
    private static final double JOL_SCORE = 2;

    public Jol(MoveStrategy moveStrategy, List<MovementPolicy> movementPolicy, Team team) {
        super(moveStrategy, movementPolicy, team, PieceType.JOL, JOL_SCORE);
    }
}