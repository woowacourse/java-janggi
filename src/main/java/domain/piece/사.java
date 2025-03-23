package domain.piece;

import domain.piece.movingStrategy.LimitedMovingStrategy;

public final class 사 extends JanggiPiece {

    public 사(final JanggiSide side) {
        super(side, JanggiPieceType.사, new LimitedMovingStrategy());
    }
}
