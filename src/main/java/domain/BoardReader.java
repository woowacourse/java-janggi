package domain;

import domain.pieces.Piece;

public interface BoardReader {

    boolean isExist(Position position);

    boolean isDifferentPieceType(Position position, Piece piece);
}
