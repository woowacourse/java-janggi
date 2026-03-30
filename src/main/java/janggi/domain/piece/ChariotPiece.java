package janggi.domain.piece;

import janggi.domain.movestrategy.MoveStrategy;

public class ChariotPiece extends Piece {
    public ChariotPiece(Team team, MoveStrategy moveStrategy) {
        super(team, Name.CHARIOT, moveStrategy);
    }

}
