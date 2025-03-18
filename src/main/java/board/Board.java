package board;

import java.util.Map;
import piece.Piece;

public class Board {

    private final Map<Position, Piece> map;

    public Board(final Map<Position, Piece> map) {
        this.map = map;
    }

    public Map<Position, Piece> getMap() {
        return map;
    }
}
