package domain.board.wing;

import domain.board.Intersection;
import domain.game.Side;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class HanWings extends Wings {

    private static final Side SIDE = Side.HAN;

    public HanWings(List<Piece> leftWingPieces, List<Piece> rightWingPieces) {
        super(leftWingPieces, rightWingPieces);

        validateSide(leftWingPieces, rightWingPieces);
    }

    @Override
    public final Map<Intersection, Piece> setUpPieces() {
        Map<Intersection, Piece> pieces = new HashMap<>();

        pieces.putAll(rightWing.setUpPieces(SIDE));
        pieces.putAll(leftWing.setUpPieces(SIDE));

        return pieces;
    }

    private static void validateSide(List<Piece> leftWingPieces, List<Piece> rightWingPieces) {
        List<Piece> entirePieces = Stream.concat(leftWingPieces.stream(), rightWingPieces.stream())
                .toList();

        List<Piece> illegalPieces = entirePieces.stream()
                .filter(piece -> piece.hasDifferentSide(SIDE))
                .toList();

        if (!illegalPieces.isEmpty()) {
            throw new IllegalArgumentException("한 진영의 진에는 한 진영의 기물만 배치할 수 있습니다. (현재 기물: " + entirePieces + ")");
        }
    }
}
