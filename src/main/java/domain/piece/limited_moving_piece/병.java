package domain.piece.limited_moving_piece;

import domain.piece.JanggiPieceType;
import domain.piece.JanggiSide;

public final class 병 extends LimitedMovingJanggiPiece {

    public 병(final JanggiSide side) {
        super(side, JanggiPieceType.병);
    }
}
