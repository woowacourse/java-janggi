package domain.board;

import domain.game.Side;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;

public class LeftWing extends Wing {

    public LeftWing(List<Piece> pieces) {
        super(pieces);
    }

    @Override
    public final Map<Intersection, Piece> setUpPieces(Side side) {
        if (side == Side.HAN) {
            return setUpHanPieces();
        }

        return setUpChoPieces();
    }

    private Map<Intersection, Piece> setUpHanPieces() {
        int row = Side.HAN.getBaseRow();

        return Map.of(
                new Intersection(row, 8), first,
                new Intersection(row, 7), second
        );
    }

    private Map<Intersection, Piece> setUpChoPieces() {
        int row = Side.CHO.getBaseRow();

        return Map.of(
                new Intersection(row, 2), first,
                new Intersection(row, 3), second
        );
    }
}
