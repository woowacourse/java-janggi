package janggi.domain;

public interface BoardView {
    boolean hasPieceAt(Position position);
    Piece getPieceAt(Position position);
}
