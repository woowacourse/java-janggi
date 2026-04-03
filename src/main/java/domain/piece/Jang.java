package domain.piece;

import domain.piece.policy.MovementPolicy;
import domain.piece.strategy.MoveStrategy;

public class Jang extends Piece {
    private static final double JANG_SCORE = 0;

    public Jang(MoveStrategy moveStrategy, MovementPolicy movementPolicy, Team team) {
        super(moveStrategy, movementPolicy, PieceType.JANG, team, JANG_SCORE);
    }
}