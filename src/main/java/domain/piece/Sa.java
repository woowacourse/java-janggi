package domain.piece;

import domain.piece.strategy.MoveStrategy;

public class Sa extends Piece {

    public Sa(MoveStrategy moveStrategy, Team team) {
        super(moveStrategy, PieceType.SA, team);
    }
}
