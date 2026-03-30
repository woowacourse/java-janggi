package janggi.domain.piece;

import janggi.domain.movestrategy.MoveStrategy;

public class GuardPiece extends Piece {

    public GuardPiece(Team team, MoveStrategy moveStrategy) {
        super(team, Name.GUARD, moveStrategy);
    }

}
