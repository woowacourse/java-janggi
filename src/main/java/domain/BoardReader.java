package domain;

public interface BoardReader {
    boolean isWithinRange(Position position);
    boolean isEmpty(Position position);
    Piece getPiece(Position position);
}
