package domain;

import domain.piece.Piece;
import domain.piece_initiaizer.PieceInitializer;
import domain.position.Position;

import java.util.Collections;
import java.util.Map;

public class Team {

    private final Country country;
    private final Map<Position, Piece> pieces;

    public Team(
            final StartingPosition startingPosition,
            final PieceInitializer initializer,
            final Country country
    ) {
        this.country = country;
        this.pieces = initializer.init(startingPosition, country);
    }

    public Map<Position, Piece> getPieces() {
        return Collections.unmodifiableMap(pieces);
    }

    public Country getCountry() {
        return country;
    }
}
