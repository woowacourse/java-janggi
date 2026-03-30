package domain.piece.factory;

import domain.game.Side;
import domain.piece.Piece;
import domain.piece.PieceType;

public final class ChariotFactory implements CompositionPieceFactory {

    @Override
    public Piece create(Side side) {
        return new Piece(PieceType.CHARIOT, side);
    }
}
