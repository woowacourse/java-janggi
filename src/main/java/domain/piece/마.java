package domain.piece;

import domain.piece.movingstrategy.LimitedMovingStrategy;

public final class 마 extends JanggiPiece {

    public 마(final JanggiSide side) {
        super(side, JanggiPieceType.마, new LimitedMovingStrategy());
    }
}
