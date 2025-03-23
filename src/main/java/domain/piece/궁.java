package domain.piece;

import domain.piece.movingStrategy.LimitedMovingStrategy;

public final class 궁 extends JanggiPiece {

    public 궁(final JanggiSide side) {
        super(side, JanggiPieceType.궁, new LimitedMovingStrategy());
    }
}
