package domain;

import domain.piece.Piece;
import java.util.Map;

public class JanggiBoard {

    private final Map<Position, Piece> board;

    public JanggiBoard(Map<Position, Piece> board) {
        this.board = board;
    }
}
