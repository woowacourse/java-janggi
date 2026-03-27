package domain.piece;

import domain.piece.strategy.MoveStrategy;

public class Sang extends Piece {
    public Sang(MoveStrategy moveStrategy, Team team) {
        super(moveStrategy, PieceType.SANG, team);
    }
}
