package janggiGame.piece;

import janggiGame.Position;
import janggiGame.piece.character.Dynasty;
import janggiGame.piece.character.PieceType;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class Piece {
    private final Dynasty dynasty;

    public Piece(Dynasty dynasty) {
        this.dynasty = Objects.requireNonNull(dynasty);
    }

    public abstract List<Position> getIntermediatePoints(Position origin, Position destination);

    public abstract PieceType getType();

    public void validateMove(Map<Position, Piece> IntermediatePointsWithPiece, Piece destinationPiece) {
        if (destinationPiece.dynasty == this.dynasty) {
            throw new UnsupportedOperationException("[ERROR] 같은 나라의 말은 공격할 수 없습니다.");
        }
    }

    public final boolean hasDynasty(Dynasty dynasty) {
        return dynasty == this.dynasty;
    }
}