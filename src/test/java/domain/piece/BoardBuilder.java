package domain.piece;

import domain.Board;
import domain.Coordinate;
import java.util.HashMap;
import java.util.Map;

public class BoardBuilder {

    private final Map<Coordinate, Piece> pieces;

    public BoardBuilder() {
        pieces = new HashMap<>();
    }

    public BoardBuilder addPiece(int x, int y, Piece piece) {
        pieces.put(new Coordinate(x, y), piece);
        return this;
    }

    public Board build() {
        return new Board(pieces);
    }
}