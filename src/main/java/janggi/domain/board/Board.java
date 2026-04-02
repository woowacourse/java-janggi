package janggi.domain.board;

import janggi.domain.Piece;
import janggi.domain.Position;
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

    public Piece placeAt(Position position) {
        return board.get(position);
    }

    public boolean isPiece(Position position) {
        return board.containsKey(position);
    }
}
