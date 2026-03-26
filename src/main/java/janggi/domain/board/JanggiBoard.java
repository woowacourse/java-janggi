package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class JanggiBoard {

    private final Map<Position, Piece> board = new HashMap<>();

    public JanggiBoard(BoardInitializer boardInitializer) {
        board.putAll(boardInitializer.initialize());
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

    public Piece findPiece(Position position) {
        return board.get(position);
    }
}
