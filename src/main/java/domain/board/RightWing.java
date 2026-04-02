package domain.board;

import domain.game.Side;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;

public class RightWing extends Wing {

    private static final int FAR_FROM_BASE_ROW = 0;

    public RightWing(List<Piece> pieces) {
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
        int row = Side.HAN.calculateRowFromBase(FAR_FROM_BASE_ROW);

        return Map.of(
                new Intersection(row, 3), first,
                new Intersection(row, 2), second
        );
    }

    private Map<Intersection, Piece> setUpChoPieces() {
        int row = Side.CHO.calculateRowFromBase(FAR_FROM_BASE_ROW);

        return Map.of(
                new Intersection(row, 7), first,
                new Intersection(row, 8), second
        );
    }
}
