package domain.piece;

import domain.piece.policy.MovementPolicy;
import domain.piece.strategy.MoveStrategy;
import java.util.List;

public class Sang extends Piece {
    private static final double SANG_SCORE = 3;

    public Sang(MoveStrategy moveStrategy, List<MovementPolicy> movementPolicy, Team team) {
        super(moveStrategy, movementPolicy, team, PieceType.SANG, SANG_SCORE);
    }
}