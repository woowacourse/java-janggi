package domain;

import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> board = new HashMap<>();

    void move(Position source, Position target) {
        Piece whatPiece = board.get(source);
        whatPiece.canMove(source, target);

    }
}
