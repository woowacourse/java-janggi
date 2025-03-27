package domain.piece;

import domain.Coordinate;
import domain.board.Board;
import java.util.List;

public abstract class Piece {
    protected final Country country;
    protected final PieceType type;

    protected Piece(Country country, PieceType type) {
        this.country = country;
        this.type = type;
    }

    public abstract List<Coordinate> availableMovePositions(Coordinate from, Board board);

    public Country getCountry() {
        return this.country;
    }

    public PieceType getType() {
        return type;
    }
}
