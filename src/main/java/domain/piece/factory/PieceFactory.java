package domain.piece.factory;

import domain.game.Side;
import domain.piece.Piece;

public interface PieceFactory {
    Piece create(Side side);
}
