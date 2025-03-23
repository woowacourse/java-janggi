package domain.piece;

import domain.piece.movingstrategy.LinearMovingStrategy;

public final class 차 extends JanggiPiece {

    public 차(final JanggiSide side) {
        super(side, JanggiPieceType.차, new LinearMovingStrategy());
    }
}
