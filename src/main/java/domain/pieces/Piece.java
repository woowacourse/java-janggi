package domain.pieces;

import domain.Country;
import domain.PieceType;
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

    public abstract List<Position> getAvailablePositions(Position nowPosition);


    protected abstract boolean canMovePosition(Position start, Position end);

    protected boolean isSameCountry(Country endCountry) {
        return country.equals(endCountry);
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
