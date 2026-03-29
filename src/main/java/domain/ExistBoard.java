package domain;

import domain.pieces.Piece;

public interface ExistBoard {

    boolean isExist(Position position);

    boolean isDifferentPieceType(Position position, Piece piece);
}
