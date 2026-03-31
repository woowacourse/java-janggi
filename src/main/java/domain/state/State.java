package domain.state;

import domain.Country;
import domain.piece.Piece;
import domain.piece.PieceInfo;
import domain.piece.PieceType;

public interface State {
    State copy();

    boolean isEmpty();

    Piece getPiece();

    PieceInfo getPieceInfo();

    PieceType getPieceType();

    Country getPieceCountry();
}
