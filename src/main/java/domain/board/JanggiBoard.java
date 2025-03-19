package domain.board;

import domain.JanggiCoordinate;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public final class JanggiBoard {
    private static final int ROW_SIZE = 9;
    private static final int COL_SIZE =10;
    private static final int BOARD_MIN_SIZE = 1;

    private final Map<JanggiCoordinate, Piece> board;

    public JanggiBoard() {
        board = JanggiBoardInitPosition.create();
    }

    public boolean hasPiece(JanggiCoordinate coordinate) {
        return board.containsKey(coordinate);
    }

    public boolean isMyTeam(JanggiCoordinate originCoordinate, JanggiCoordinate coordinate) {
        return hasPiece(coordinate) && board.get(originCoordinate).getTeam() == board.get(coordinate).getTeam();
    }

    public boolean isOutOfBoundary(int row, int col) {
        if (row < BOARD_MIN_SIZE || row > ROW_SIZE) {
            return true;
        }
        return col < BOARD_MIN_SIZE || col > COL_SIZE;
    }

    public Map<JanggiCoordinate, Piece> getBoard() {
        return board;
    }
}
