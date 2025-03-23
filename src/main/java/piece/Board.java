package piece;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Pieces allPieces;

    public Board(Pieces allPieces) {
        this.allPieces = allPieces;
    }

    public Map<Position, Piece> positionPieces() {
        List<Piece> allPieces = this.allPieces.getPieces();
        Map<Position, Piece> playerBoard = new HashMap<>();
        for (Piece piece : allPieces) {
            playerBoard.put(piece.getPosition(), piece);
        }
        return playerBoard;
    }
}
