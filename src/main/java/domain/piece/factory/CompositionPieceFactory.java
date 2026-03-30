package domain.piece.factory;

import domain.game.Side;
import domain.piece.Piece;

public interface CompositionPieceFactory {
    Piece create(Side side);
}
