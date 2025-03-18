import java.util.HashMap;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> pieces;

    private Board(Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public static Board createEmpty() {
        Map<Position, Piece> cells = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                cells.put(new Position(i, j), Piece.createEmpty());
            }
        }
        return new Board(cells);
    }

    public Map<Position, Piece> getPieces() {
        return pieces;
    }
}
