package domain.piece;

import domain.piece.movingStrategy.LimitedMovingStrategy;

public final class 상 extends JanggiPiece {

    public 상(final JanggiSide side) {
        super(side, JanggiPieceType.상, new LimitedMovingStrategy());
    }
}
