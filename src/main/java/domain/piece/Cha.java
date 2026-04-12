package domain.piece;

import domain.piece.policy.MovementPolicy;
import domain.piece.strategy.MoveStrategy;
import java.util.List;

public class Cha extends Piece {
    private static final double CHA_SCORE = 13;

    public Cha(MoveStrategy moveStrategy, List<MovementPolicy> movementPolicy, Team team) {
        super(moveStrategy, movementPolicy, team, PieceType.CHA, CHA_SCORE);
    }
}