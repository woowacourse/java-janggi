package domain.piece;

import domain.piece.policy.MovementPolicy;
import domain.piece.strategy.MoveStrategy;
import java.util.List;

public class Byeong extends Piece {
    private static final double BYEONG_SCORE = 2;

    public Byeong(MoveStrategy moveStrategy, List<MovementPolicy> movementPolicy, Team team) {
        super(moveStrategy, movementPolicy, team, PieceType.BYEONG, BYEONG_SCORE);
    }
}
