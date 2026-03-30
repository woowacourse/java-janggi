package janggi.domain.piece;

import janggi.domain.movestrategy.MoveStrategy;

public class SoliderPiece extends Piece {
    public SoliderPiece(Team team, MoveStrategy moveStrategy) {
        super(team, Name.SOLDIER, moveStrategy);
    }
}
