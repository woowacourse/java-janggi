package domain.piece;

import domain.piece.strategy.MoveStrategy;

public class Byeong extends NonJumpable {
    public Byeong(MoveStrategy moveStrategy, Team team) {
        super(moveStrategy, PieceType.BYEONG, team);
    }
}
