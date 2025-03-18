import java.util.HashMap;
import java.util.Map;

public class Board {
    private final Map<Integer, Piece> pieces;

    private Board(Map<Integer, Piece> pieces) {
        this.pieces = pieces;
    }

    public static Board createEmpty() {
        Map<Integer, Piece> cells = new HashMap<>();
        for(int i = 0; i < 90; i ++) {
            cells.put(i, Piece.createEmpty());
        }
        return new Board(cells);
    }

    public Map<Integer, Piece> getPieces() {
        return pieces;
    }
}
