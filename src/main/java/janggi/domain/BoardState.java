package janggi.domain;

public interface BoardState {
    boolean hasPieceAt(Position position);
    Piece getPieceAt(Position position);
}
