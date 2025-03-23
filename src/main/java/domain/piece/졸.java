package domain.piece;

import domain.piece.movingstrategy.LimitedMovingStrategy;

public final class 졸 extends JanggiPiece {

    public 졸(final JanggiSide side) {
        super(side, JanggiPieceType.졸, new LimitedMovingStrategy());
    }
}
