package domain.state;

import domain.country.CountryType;
import domain.piece.Piece;
import domain.piece.PieceInfo;
import domain.piece.PieceType;

public interface State {
    State copy();

    boolean isEmpty();

    Piece getPiece();

    PieceInfo getPieceInfo();

    PieceType getPieceType();

    double getPieceScore();

    CountryType getPieceCountryType();
}
