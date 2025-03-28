package janggi.domain.piece.behavior.palace;

import janggi.domain.piece.PieceType;

public final class Guard extends PalaceBehavior {

    @Override
    public String toName() {
        return PieceType.GUARD.getName();
    }

    @Override
    public int toScore() {
        return PieceType.GUARD.getScore();
    }
}
