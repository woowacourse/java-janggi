package domain.piece.factory;

import domain.game.Side;
import domain.piece.Piece;
import domain.piece.PieceType;

public final class SoldierFactory implements CompositionPieceFactory {

    @Override
    public Piece create(Side side) {
        return new Piece(PieceType.SOLDIER, side);
    }
}
