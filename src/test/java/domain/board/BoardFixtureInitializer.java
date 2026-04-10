package domain.board;

import domain.coordinate.Position;
import domain.piece.EmptyPiece;
import domain.piece.Piece;

import java.util.HashMap;
import java.util.Map;

public class BoardFixtureInitializer {

    private final Map<Position, Piece> pieces = new HashMap<>();

    public BoardFixtureInitializer() {
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 9; c++) {
                pieces.put(Position.of(r, c), EmptyPiece.getInstance());
            }
        }
    }

    public BoardFixtureInitializer put(Position position, Piece piece) {
        pieces.put(position, piece);
        return this;
    }

    public Board build() {
        return new Board(pieces);
    }
}
