package janggi.fixture;

import janggi.board.Board;
import janggi.piece.Piece;
import janggi.position.Position;
import java.util.Map;

public class TestBoardGenerator {

    public static Board generateEmpty() {
        return new Board(Map.of());
    }

    public static Board generateBoardWithOnePiece(
            final Position position, final Piece piece
    ) {
        Map<Position, Piece> board = Map.of(
                position, piece
        );
        return new Board(board);
    }

    public static Board generateBoardWithTwoPiece(
            final Position position1, final Piece piece1,
            final Position position2, final Piece piece2
    ) {
        Map<Position, Piece> board = Map.of(
                position1, piece1,
                position2, piece2
        );
        return new Board(board);
    }
}
