package janggiGame.piece;

import janggiGame.Dot;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class Piece {
    private final Dynasty dynasty;

    public Piece(Dynasty dynasty) {
        this.dynasty = Objects.requireNonNull(dynasty);
    }

    public final Dynasty getDynasty() {
        return dynasty;
    }

    public abstract List<Dot> getIntermediatePoints(Dot origin, Dot destination);

    public void validateMove(Map<Dot, Piece> IntermediatePointsWithPiece, Piece destinationPiece) {
        if (destinationPiece != null && destinationPiece.dynasty == this.dynasty) {
            throw new UnsupportedOperationException("[ERROR] 같은 나라의 말은 공격할 수 없습니다.");
        }
    }

    public abstract PieceType getType();
}