package domain.board;

import domain.coordinate.Position;
import domain.piece.EmptyPiece;
import domain.piece.Piece;

import java.util.HashMap;
import java.util.Map;

public class BoardFixtureInitializer {

    private static final int COL_SIZE = 10;
    private static final int ROW_SIZE = 9;

    private final Map<Position, Piece> pieces = new HashMap<>();

    public BoardFixtureInitializer() {
        for (int i = 0; i < COL_SIZE; i++) {
            for (int j = 0; j < ROW_SIZE; j++) {
                pieces.put(Position.of(i, j), EmptyPiece.getInstance());
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
