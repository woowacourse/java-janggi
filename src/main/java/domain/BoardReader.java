package domain;

import domain.piece.Piece;

public interface BoardReader {

    boolean isExist(Position position);

    boolean isDifferentPieceType(Position position, Piece piece);
}
