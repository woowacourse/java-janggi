package domain.piece;

import domain.piece.strategy.MoveStrategy;

public class Jang extends NonJumpable {
    public Jang(MoveStrategy moveStrategy, Team team) {
        super(moveStrategy, PieceType.JANG, team);
    }

    public Jang(Long id, MoveStrategy moveStrategy, Team team) {
        super(id, moveStrategy, PieceType.JANG, team);
    }
}
