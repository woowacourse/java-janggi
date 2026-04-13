package domain.pieces;

import domain.PieceFinder;
import domain.enums.Country;
import domain.enums.Direction;
import domain.enums.PieceType;
import domain.Position;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class Piece{
    private final Country country;
    private final PieceType pieceType;

    public Piece(Country country, PieceType pieceType) {
        this.country = country;
        this.pieceType = pieceType;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public int getPieceScore() {
        return pieceType.getScore();
    }

    public Optional<Position> move(Position start, Direction direction) {
        int startX = start.getX();
        int startY = start.getY();
        int forward = country.getForward();
        int dx = direction.getDx();
        int dy = direction.getDy();
        try {
            return Optional.of(Position.create(startX + dx * forward, startY + dy * forward));
        } catch(IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public abstract List<Position> getAvailableRoute(Position start, PieceFinder finder);

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
