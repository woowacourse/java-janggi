package domain.piece;

import domain.piece.movingStrategy.LimitedMovingStrategy;

public final class 병 extends JanggiPiece {

    public 병(final JanggiSide side) {
        super(side, JanggiPieceType.병, new LimitedMovingStrategy());
    }
}
