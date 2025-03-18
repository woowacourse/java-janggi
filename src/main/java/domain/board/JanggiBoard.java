package domain.board;

import domain.JanggiCoordinate;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public final class JanggiBoard {
    private final Map<JanggiCoordinate, Piece> board;

    public JanggiBoard() {
        board = new HashMap<>();
    }
    public boolean isMyTeam(JanggiCoordinate originCoordinate,JanggiCoordinate coordinate){

    public boolean isOutOfBoundary(int row, int col) {
        if (row < 1 || row > 9) {
            return true;
        }
        return col < 1 || col > 10;
    }
}
