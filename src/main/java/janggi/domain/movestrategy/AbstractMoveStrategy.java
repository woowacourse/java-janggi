package janggi.domain.movestrategy;

import janggi.domain.piece.Piece;

public abstract class AbstractMoveStrategy implements MoveStrategy {

    @Override
    public boolean canCapture(Piece from, Piece to) {
        return to == null || !from.isSameTeam(to);
    }
}
