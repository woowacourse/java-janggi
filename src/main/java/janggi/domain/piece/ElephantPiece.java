package janggi.domain.piece;

import janggi.domain.movestrategy.MoveStrategy;

public class ElephantPiece extends Piece {

    public ElephantPiece(Team team, MoveStrategy moveStrategy) {
        super(team, Name.ELEPHANT, moveStrategy);
    }

}
