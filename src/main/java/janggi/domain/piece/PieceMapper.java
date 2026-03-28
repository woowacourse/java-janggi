package janggi.domain.piece;

import janggi.domain.game.Side;

public interface PieceMapper<T> {

    T apply(Side side, PieceType type, String pieceNumber);
}
