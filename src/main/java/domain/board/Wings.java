package domain.board;

import domain.game.Side;
import domain.piece.Piece;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public abstract class Wings {

    protected final LeftWing leftWing;
    protected final RightWing rightWing;

    public Wings(
            List<Piece> leftWingPieces,
            List<Piece> rightWingPieces,
            Side owningSide
    ) {
        List<Piece> entirePieces = Stream.concat(leftWingPieces.stream(), rightWingPieces.stream())
                .toList();
        validateSide(entirePieces, owningSide);

        this.leftWing = new LeftWing(leftWingPieces);
        this.rightWing = new RightWing(rightWingPieces);
    }

    public abstract Map<Intersection, Piece> setUpPieces();

    private void validateSide(Collection<Piece> pieces, Side owningSide) {
        List<Piece> illegalPieces = pieces.stream()
                .filter(piece -> piece.hasDifferentSide(owningSide))
                .toList();

        if (!illegalPieces.isEmpty()) {
            throw new IllegalArgumentException("진영에는 같은 소속의 기물만 배치할 수 있습니다. (현재 진영: " + owningSide + " 현재 기물: " + pieces + ")");
        }
    }
}
