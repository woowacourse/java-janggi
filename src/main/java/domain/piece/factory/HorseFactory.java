package domain.piece.factory;

import domain.game.Side;
import domain.piece.CompositionPiece;
import domain.piece.PieceType;

public final class HorseFactory implements CompositionPieceFactory {

    @Override
    public CompositionPiece create(Side side) {
        return new CompositionPiece(PieceType.HORSE, side);
    }
}
