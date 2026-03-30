package janggi.domain.piece;

import janggi.domain.movestrategy.MoveStrategy;

public class GeneralPiece extends Piece {

    public GeneralPiece(Team team, MoveStrategy moveStrategy) {
        super(team, Name.GENERAL, moveStrategy);
    }

}
