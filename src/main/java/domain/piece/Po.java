package domain.piece;

import domain.piece.strategy.MoveStrategy;

public class Po extends Piece {
    public Po(MoveStrategy moveStrategy, Team team) {
        super(moveStrategy, PieceType.PO, team);
    }
}
