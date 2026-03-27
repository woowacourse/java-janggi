package domain.piece;

import domain.piece.strategy.MoveStrategy;

public class Byeong extends Piece {
    public Byeong(MoveStrategy moveStrategy, Team team) {
        super(moveStrategy, PieceType.BYEONG, team);
    }
}
