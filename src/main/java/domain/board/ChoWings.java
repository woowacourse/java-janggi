package domain.board;

import domain.game.Side;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChoWings extends Wings {

    private static final Side SIDE = Side.CHO;

    public ChoWings(List<Piece> leftWingPieces, List<Piece> rightWingPieces) {
        super(leftWingPieces, rightWingPieces, SIDE);
    }

    @Override
    public final Map<Intersection, Piece> setUpPieces() {
        Map<Intersection, Piece> pieces = new HashMap<>();

        pieces.putAll(leftWing.setUpPieces(SIDE));
        pieces.putAll(rightWing.setUpPieces(SIDE));

        return pieces;
    }
}
