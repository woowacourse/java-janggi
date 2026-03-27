package domain.board;

import domain.Position;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> board = new HashMap<>();

    void move(Position source, Position target) {
        Piece whatPiece = board.get(source);
        whatPiece.canMove(source, target);
    }

    /*
    public boolean checkRoute(ActivePiece piece, List<Position> routes) {
        if (piece.) {
            if (board.get(routes.get(0)).isNotEmpty()) {
                if (piece.isSameTeam((ActivePiece) board.get(routes.get(0)))) {
                    return false;
                }
            }
        }


        return true; */
}
