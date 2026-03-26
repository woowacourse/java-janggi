package domain;

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
