package domain.piece.factory;

import domain.game.Side;
import domain.piece.Piece;
import domain.piece.PieceType;

public final class ChariotFactory implements PieceFactory {

    @Override
    public Piece create(Side side) {
        return Piece.of(PieceType.CHARIOT, side);
    }
}
