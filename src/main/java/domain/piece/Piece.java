package domain.piece;

import domain.Coordinate;
import domain.board.Board;
import java.util.List;

abstract public class Piece {
    protected final Country country;
    protected final PieceType type;

    public Piece(Country country, PieceType type) {
        this.country = country;
        this.type = type;
    }

    abstract public List<Coordinate> availableMovePositions(Coordinate from, Board board);

    public Country getCountry() {
        return this.country;
    }

    public PieceType getType() {
        return type;
    }
}
