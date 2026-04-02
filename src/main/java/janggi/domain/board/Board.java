package janggi.domain.board;

import janggi.domain.common.Position;
import janggi.domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board = new HashMap<>();

    public void place(Position position, Piece piece) {
        board.put(position, piece);
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

    public void movePiece(Position movePiecePosition, Position destination) {
        Piece piece = board.get(movePiecePosition);
        board.remove(movePiecePosition);
        board.put(destination, piece);
    }

    public Piece pieceAt(Position position) {
        return board.get(position);
    }

    public boolean hasPiece(Position position) {
        return board.containsKey(position);
    }
}
