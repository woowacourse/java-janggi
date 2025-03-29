package piece;

import java.util.Objects;
import movementRule.PieceRule;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;

public class Piece {

    private final PieceRule pieceRule;

    public Piece(PieceRule pieceRule) {
        this.pieceRule = pieceRule;
    }

    public Positions makeRoute(final Position startPosition, final Position destination) {
        return pieceRule.makeRoute(startPosition, destination);
    }

    public void canMoveTo(final Position startPosition, final Position destination) {
        pieceRule.canMoveTo(startPosition, destination);
    }

    public boolean isPo() {
        return pieceRule.isPo();
    }

    public boolean isJanggun() {
        return pieceRule.isJanggun();
    }

    public PieceType getPieceType() {
        return pieceRule.getPieceType();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return Objects.equals(pieceRule, piece.pieceRule);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pieceRule);
    }
}
