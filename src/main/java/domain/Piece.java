package domain;

import domain.constant.Country;
import domain.constant.PieceType;
import java.util.List;
import java.util.Objects;

public class Piece {
    private static final Piece EMPTY = new Piece(Country.NONE, PieceType.NONE);

    private final Country country;
    private final PieceType pieceType;

    public Piece(Country country, PieceType pieceType) {
        this.country = country;
        this.pieceType = pieceType;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public boolean canMovePosition(Position start, Position end) {
        return pieceType.canMovePosition(start, end, this);
    }

    public boolean isAvailableRoute(List<Piece> pieces, PieceType endPieceType) {
        return pieceType.isAvailableRoute(pieces, endPieceType);
    }

    public boolean isDifferentCountry(Country endCountry) {
        return !country.equals(endCountry);
    }

    public Country getCountry() {
        return country;
    }

    public boolean isEmpty() {
        return this.pieceType == PieceType.NONE;
    }

    public static Piece getEmptyPiece() {
        return EMPTY;
    }

    public int getScore() {
        return pieceType.getScore();
    }

    public boolean isPalacePiece() {
        return pieceType.equals(PieceType.SA) || pieceType.equals(PieceType.JANG);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return country == piece.country && pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, pieceType);
    }
}
