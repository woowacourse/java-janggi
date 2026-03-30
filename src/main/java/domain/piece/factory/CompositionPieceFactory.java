package domain.piece.factory;

import domain.game.Side;
import domain.piece.CompositionPiece;

public interface CompositionPieceFactory {
    CompositionPiece create(Side side);
}
