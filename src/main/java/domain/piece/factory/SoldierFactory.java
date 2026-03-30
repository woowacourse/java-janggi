package domain.piece.factory;

import domain.game.Side;
import domain.piece.CompositionPiece;
import domain.piece.PieceType;

public final class SoldierFactory implements CompositionPieceFactory {

    @Override
    public CompositionPiece create(Side side) {
        return new CompositionPiece(PieceType.SOLDIER, side);
    }
}
