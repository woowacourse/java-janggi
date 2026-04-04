package janggi.domain;

public interface BoardState { //인터페이스명 (readable)
    boolean hasPieceAt(Position position);
    Piece getPieceAt(Position position);
}
