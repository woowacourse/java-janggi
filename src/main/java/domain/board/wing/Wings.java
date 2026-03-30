package domain.board.wing;

import domain.board.Intersection;
import domain.game.Side;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public final class Wings {

    private final Side side;
    private final LeftWing leftWing;
    private final RightWing rightWing;

    public Wings(Side side, List<Piece> leftWingPieces, List<Piece> rightWingPieces) {
        validateSide(side, leftWingPieces, rightWingPieces);

        this.side = side;
        this.leftWing = new LeftWing(leftWingPieces);
        this.rightWing = new RightWing(rightWingPieces);
    }

    public final Map<Intersection, Piece> setUpPieces() {
        Map<Intersection, Piece> pieces = new HashMap<>();

        pieces.putAll(leftWing.setUpPieces(side));
        pieces.putAll(rightWing.setUpPieces(side));

        return pieces;
    }

    private void validateSide(Side side, List<Piece> leftWingPieces, List<Piece> rightWingPieces) {
        List<Piece> entirePieces = Stream.concat(leftWingPieces.stream(), rightWingPieces.stream())
                .toList();

        List<Piece> illegalPieces = entirePieces.stream()
                .filter(piece -> piece.hasDifferentSide(side))
                .toList();

        if (!illegalPieces.isEmpty()) {
            throw new IllegalArgumentException(
                    side + " 진영의 진에는 " + side + "진영의 기물만 배치할 수 있습니다. (현재 기물: " + entirePieces + ")");
        }
    }
}
