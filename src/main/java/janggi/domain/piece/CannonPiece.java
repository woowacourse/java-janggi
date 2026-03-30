package janggi.domain.piece;

import janggi.domain.movestrategy.MoveStrategy;

public class CannonPiece extends Piece {
    public CannonPiece(Team team, MoveStrategy moveStrategy) {
        super(team, Name.CANNON, moveStrategy);
    }
}
