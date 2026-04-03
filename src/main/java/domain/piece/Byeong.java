package domain.piece;

import domain.piece.policy.MovementPolicy;
import domain.piece.strategy.MoveStrategy;

public class Byeong extends Piece {
    private static final double BYEONG_SCORE = 2;

    public Byeong(MoveStrategy moveStrategy, MovementPolicy movementPolicy, Team team) {
        super(moveStrategy, movementPolicy, PieceType.BYEONG, team, BYEONG_SCORE);
    }
}
