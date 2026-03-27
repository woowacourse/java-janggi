package domain.piece;

import domain.piece.strategy.MoveStrategy;

public class Jang extends Piece {
    public Jang(MoveStrategy moveStrategy, Team team) {
        super(moveStrategy, PieceType.JANG, team);
    }
}
