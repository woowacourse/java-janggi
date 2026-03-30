package janggi.domain.piece;

import janggi.domain.movestrategy.MoveStrategy;

public class HorsePiece extends Piece {

    public HorsePiece(Team team, MoveStrategy moveStrategy) {
        super(team, Name.HORSE, moveStrategy);
    }

}
