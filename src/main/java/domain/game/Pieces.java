package domain.game;

import domain.vo.Arrangements;
import java.util.Map;

public class Pieces {
    private final Map<Position, Piece> pieces;

    public Pieces(Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public static Pieces of(Arrangements arrangements) {
        return new Pieces(PositionLayout.build(arrangements));
    }
}
