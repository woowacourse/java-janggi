package domain.piece;

import domain.piece.strategy.MoveStrategy;

public class Jang extends NonJumpable {
    public Jang(MoveStrategy moveStrategy, Team team) {
        super(moveStrategy, PieceType.JANG, team);
    }
}
