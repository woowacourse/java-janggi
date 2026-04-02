package janggi.domain;

import janggi.domain.position.Position;

public interface BoardState {
    boolean hasPieceAt(Position position);
    Piece getPieceAt(Position position);
}
