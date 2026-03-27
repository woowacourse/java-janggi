package domain.piece;

import domain.piece.strategy.MoveStrategy;

public class Cha extends Piece {

    public Cha(MoveStrategy moveStrategy, Team team) {
        super(moveStrategy, PieceType.CHA, team);
    }
}
