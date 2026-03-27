package domain.piece;

import domain.piece.strategy.MoveStrategy;

public class Ma extends Piece {

    public Ma(MoveStrategy moveStrategy, Team team) {
        super(moveStrategy, PieceType.MA, team);
    }
}
