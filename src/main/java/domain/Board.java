package domain;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> board;

    public Board() {
        board = new HashMap<>();
    }

    public PieceType getPiece(Position position){
        return board.get(position).getPieceType();
    }
}
