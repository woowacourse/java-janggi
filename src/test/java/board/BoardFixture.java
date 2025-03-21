package board;

import coordinate.Coordinate;
import java.util.HashMap;
import java.util.Map;
import piece.Piece;

public class BoardFixture {

    private final Map<Coordinate, Piece> pieces;

    public BoardFixture() {
        pieces = new HashMap<>();
    }

    public BoardFixture addPiece(int x, int y, Piece piece) {
        pieces.put(new Coordinate(x, y), piece);
        return this;
    }

    public Board build() {
        return new Board(pieces);
    }
}
