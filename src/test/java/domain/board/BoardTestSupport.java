package domain.board;

import domain.pieces.EmptyPiece;
import domain.pieces.Piece;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;

public class BoardTestSupport {

    private static final Piece EMPTY_PIECE = new EmptyPiece();

    public static Map<Position, Piece> emptyChoBoard() {
        Map<Position, Piece> board = new HashMap<>();
        for (int row = 0; row <= 4; row++) {
            for (int column = 0; column <= 8; column++) {
                board.put(new Position(row, column), EMPTY_PIECE);
            }
        }
        return board;
    }

    public static Map<Position, Piece> emptyHanBoard() {
        Map<Position, Piece> board = new HashMap<>();
        for (int row = 5; row <= 9; row++) {
            for (int column = 0; column <= 8; column++) {
                board.put(new Position(row, column), EMPTY_PIECE);
            }
        }
        return board;
    }
}
