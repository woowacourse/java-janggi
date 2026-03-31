package domain.pieces;

import domain.enums.Country;
import domain.enums.PieceType;
import domain.Position;

import java.util.List;
import java.util.Objects;

public abstract class Piece {
    private final Country country;
    private final PieceType pieceType;

    public Piece(Country country, PieceType pieceType) {
        this.country = country;
        this.pieceType = pieceType;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public abstract boolean canMovePosition(Position start, Position end);

    public abstract boolean isAvailableRoute(List<Piece> pieces, PieceType endPieceType);

//    public abstract List<Position> getAvailableRoute();
    public boolean isDifferentCountry(Country endCountry) {
        return !country.equals(endCountry);
    }

    public Country getCountry() {
        return country;
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
