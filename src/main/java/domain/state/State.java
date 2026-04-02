package domain.state;

import domain.piece.Piece;

public interface State {
    Piece getPiece();

    boolean isEmpty();
}
