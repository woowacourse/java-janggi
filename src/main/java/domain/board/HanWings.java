package domain.board;

import domain.game.Side;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HanWings extends Wings {

    private static final Side SIDE = Side.HAN;

    public HanWings(List<Piece> leftWingPieces, List<Piece> rightWingPieces) {
        super(leftWingPieces, rightWingPieces, SIDE);
    }

    @Override
    public final Map<Intersection, Piece> setUpPieces() {
        Map<Intersection, Piece> pieces = new HashMap<>();

        pieces.putAll(rightWing.setUpPieces(SIDE));
        pieces.putAll(leftWing.setUpPieces(SIDE));

        return pieces;
    }
}
