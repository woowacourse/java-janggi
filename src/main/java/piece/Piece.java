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

    public Positions makeRoute(Position destination) {
        return pieceRule.makeRoute(destination);
    }

    public void canMoveTo(Position destination) {
        pieceRule.canMoveTo(destination);
    }

    public boolean isPo() {
        return pieceRule.isPo();
    }

    public boolean isJanggun() {
        return pieceRule.isJanggun();
    }

    public Position currentPosition() {
        return pieceRule.currentPosition();
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
