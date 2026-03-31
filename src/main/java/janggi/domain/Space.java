package janggi.domain;

import janggi.domain.piece.Piece;

public interface Space {

    boolean isBlank();

    String displayValue();

    Piece asPiece();
}
