package janggi.board;

import janggi.piece.Empty;
import janggi.piece.Piece;

import java.util.HashMap;
import java.util.Map;

public class JanggiBoard {

    private static final int ROW_SIZE = 9;
    private static final int COL_SIZE = 10;

    private final Map<Position, Piece> board;

    private JanggiBoard(final Map<Position, Piece> board) {
        this.board = board;
    }

    public static JanggiBoard initialize() {
        Map<Position, Piece> board = new HashMap<>();

        for (int row = 0; row < ROW_SIZE; row++) {
            for (int col = 0; col < COL_SIZE; col++) {
                board.put(new Position(row, col), new Empty());
            }
        }

        return new JanggiBoard(board);
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }
}
